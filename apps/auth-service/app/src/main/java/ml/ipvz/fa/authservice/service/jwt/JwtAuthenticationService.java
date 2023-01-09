package ml.ipvz.fa.authservice.service.jwt;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ml.ipvz.fa.authservice.exception.AccessTokenExpiredException;
import ml.ipvz.fa.authservice.exception.RefreshTokenExpiredException;
import ml.ipvz.fa.authservice.exception.RefreshTokenInvalidException;
import ml.ipvz.fa.authservice.exception.RefreshTokenNotFoundException;
import ml.ipvz.fa.authservice.model.CustomClaims;
import ml.ipvz.fa.authservice.model.config.TokenConfig;
import ml.ipvz.fa.authservice.model.entity.ClientEntity;
import ml.ipvz.fa.authservice.model.token.AccessRefreshToken;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import ml.ipvz.fa.authservice.model.token.Token;
import ml.ipvz.fa.authservice.repository.AuthClientRepository;
import ml.ipvz.fa.authservice.service.AuthenticationService;
import ml.ipvz.fa.authservice.service.RefreshTokenService;
import ml.ipvz.fa.authservice.service.TokenService;
import ml.ipvz.fa.userservice.client.UserServiceClient;
import ml.ipvz.fa.userservice.model.LoginDto;
import ml.ipvz.fa.userservice.model.UserDto;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService implements AuthenticationService {

    private final UserServiceClient userServiceClient;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthClientRepository clientRepository;
    private final ObjectMapper mapper;
    private final TokenConfig config;
    private final Clock clock;

    @Override
    @Transactional
    public Mono<AccessRefreshToken> login(Mono<LoginDto> loginDto) {
        return userServiceClient.login(loginDto)
                .flatMap(this::generateTokenPair);
    }

    private Mono<AccessRefreshToken> generateTokenPair(UserDto user) {
        return generateAccessToken(user)
                .zipWith(refreshTokenService.generate(config.getRefreshLength()))
                .zipWhen(t -> saveClient(String.valueOf(user.getId()), t.getT2(), clock.instant()))
                .doOnNext(t -> log.info("Generated token pair for user {}", t.getT2().id()))
                .map(t -> new AccessRefreshToken(t.getT1().getT1(), t.getT1().getT2()));
    }

    @SneakyThrows
    private Mono<String> generateAccessToken(UserDto user) {
        Map<String, Object> claims = Map.of(
                CustomClaims.USER, mapper.writeValueAsBytes(user),
                Claims.ID, user.getId(),
                Claims.SUBJECT, user.getLogin()
        );
        return tokenService.generate(claims);
    }

    private Mono<ClientEntity> saveClient(String id, String refreshToken, Instant updated) {
        return clientRepository.findById(id)
                .flatMap(c -> clientRepository.save(new ClientEntity(id, refreshToken, updated)))
                .switchIfEmpty(clientRepository.insert(id, refreshToken, updated).then(clientRepository.findById(id)));
    }

    @Override
    public Mono<AccessRefreshToken> refresh(Mono<AccessRefreshToken> accessRefreshToken) {
        return accessRefreshToken.flatMap(a ->
                parseClaims(a.accessToken())
                        .flatMap(r -> getUserFromClaims(r.claims()))
                        .zipWhen(u -> findClient(String.valueOf(u.getId())))
                        .doOnNext(t -> validateRefreshToken(a.refreshToken(), t.getT2()))
                        .flatMap(t -> generateTokenPair(t.getT1())));
    }

    @SneakyThrows
    private Mono<UserDto> getUserFromClaims(Claims claims) {
        return Mono.just(mapper.readValue(Base64.decode(claims.get(CustomClaims.USER, String.class)), UserDto.class));
    }

    private Mono<ClientEntity> findClient(String id) {
        return clientRepository.findById(id).switchIfEmpty(Mono.error(new RefreshTokenNotFoundException(id)));
    }

    private void validateRefreshToken(String refreshToken, ClientEntity client) {
        if (!client.refreshToken().equals(refreshToken)) {
            throw new RefreshTokenInvalidException(refreshToken);
        } else if (clock.instant().isAfter(client.updated().plus(config.getRefreshDuration()))) {
            throw new RefreshTokenExpiredException();
        }
    }

    private Mono<ParseTokenResult> parseClaims(String token) {
        return tokenService.parse(token)
                .map(claims -> new ParseTokenResult(claims, Optional.empty()))
                .onErrorResume(ExpiredJwtException.class, (e) -> Mono.just(new ParseTokenResult(e.getClaims(),
                        Optional.of(e))));
    }

    @Override
    public Mono<AccessToken> check(Mono<Token> token) {
        return token.flatMap(t -> parseClaims(t.getAccessToken()))
                .flatMap(r -> r.error.<Mono<ParseTokenResult>>map(throwable -> Mono.error(new AccessTokenExpiredException(throwable))).orElseGet(() -> Mono.just(r)))
                .flatMap(r -> getUserFromClaims(r.claims))
                .flatMap(this::generateAccessToken)
                .map(AccessToken::new);
    }


    private record ParseTokenResult(
            Claims claims,
            Optional<Throwable> error
    ) {
    }
}

package ml.ipvz.fa.authservice.service.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.ipvz.fa.authservice.base.model.User;
import ml.ipvz.fa.authservice.base.util.JwtUtils;
import ml.ipvz.fa.authservice.exception.AccessTokenExpiredException;
import ml.ipvz.fa.authservice.exception.RefreshTokenNotFoundException;
import ml.ipvz.fa.authservice.model.config.AccessTokenType;
import ml.ipvz.fa.authservice.model.entity.ClientEntity;
import ml.ipvz.fa.authservice.model.token.AccessRefreshToken;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import ml.ipvz.fa.authservice.model.token.Token;
import ml.ipvz.fa.authservice.repository.AuthClientRepository;
import ml.ipvz.fa.authservice.service.AccessTokenService;
import ml.ipvz.fa.authservice.service.AuthenticationService;
import ml.ipvz.fa.authservice.service.RefreshTokenService;
import ml.ipvz.fa.userservice.client.UserServiceClient;
import ml.ipvz.fa.userservice.model.LoginDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService implements AuthenticationService {

    private final UserServiceClient userServiceClient;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthClientRepository clientRepository;
    private final Clock clock;

    @Override
    @Transactional
    public Mono<AccessRefreshToken> login(Mono<LoginDto> loginDto) {
        return userServiceClient.login(loginDto)
                .map(dto -> new User(dto.getId(), dto.getLogin(),
                        dto.getPermissions().stream().map(Objects::toString).toList()))
                .flatMap(this::generateTokenPair);
    }

    private Mono<AccessRefreshToken> generateTokenPair(User user) {
        return generateExternalAccessToken(user)
                .zipWith(generateRefreshToken(user))
                .zipWhen(t -> saveClient(String.valueOf(user.id()), t.getT2(), clock.instant()))
                .doOnNext(t -> log.info("Generated token pair for user {}", t.getT2().id()))
                .map(t -> new AccessRefreshToken(t.getT1().getT1(), t.getT1().getT2()));
    }

    private Mono<String> generateExternalAccessToken(User user) {
        return accessTokenService.generate(user, AccessTokenType.EXTERNAL);
    }

    private Mono<String> generateInternalAccessToken(User user) {
        return accessTokenService.generate(user, AccessTokenType.INTERNAL);
    }

    private Mono<String> generateRefreshToken(User user) {
        return refreshTokenService.generate(user);
    }

    private Mono<ClientEntity> saveClient(String id, String refreshToken, Instant updated) {
        return clientRepository.findById(id)
                .flatMap(c -> clientRepository.save(new ClientEntity(id, refreshToken, updated)))
                .switchIfEmpty(clientRepository.insert(id, refreshToken, updated).then(clientRepository.findById(id)));
    }

    @Override
    public Mono<AccessRefreshToken> refresh(Mono<AccessRefreshToken> accessRefreshToken) {
        return accessRefreshToken.flatMap(a ->
                parseExternalToken(a.accessToken())
                        .map(r -> r.user)
                        .zipWhen(u -> findClient(String.valueOf(u.id())))
                        .doOnNext(t -> validateRefreshToken(a.refreshToken(), t.getT2()))
                        .flatMap(t -> generateTokenPair(t.getT1())));
    }

    private Mono<ClientEntity> findClient(String id) {
        return clientRepository.findById(id).switchIfEmpty(Mono.error(new RefreshTokenNotFoundException(id)));
    }

    private void validateRefreshToken(String refreshToken, ClientEntity client) {
        refreshTokenService.validate(refreshToken, client);
    }

    private Mono<ParseTokenResult> parseExternalToken(String token) {
        return accessTokenService.parse(token, AccessTokenType.EXTERNAL)
                .map(ParseTokenResult::new)
                .onErrorResume(ExpiredJwtException.class, (e) -> Mono.just(new ParseTokenResult(e.getClaims(), e)));
    }

    @Override
    public Mono<AccessToken> check(Mono<Token> token) {
        return token.flatMap(t -> parseExternalToken(t.getAccessToken()))
                .flatMap(r -> r.error
                        .<Mono<ParseTokenResult>>map(throwable -> Mono.error(new AccessTokenExpiredException(throwable)))
                        .orElseGet(() -> Mono.just(r)))
                .map(r -> r.user)
                .flatMap(this::generateInternalAccessToken)
                .map(AccessToken::new);
    }


    private record ParseTokenResult(
            User user,
            Optional<Throwable> error
    ) {
        private ParseTokenResult(User user) {
            this(user, Optional.empty());
        }

        private ParseTokenResult(Claims claims, Throwable throwable) {
            this(JwtUtils.getUserFromClaims(claims), Optional.ofNullable(throwable));
        }
    }
}

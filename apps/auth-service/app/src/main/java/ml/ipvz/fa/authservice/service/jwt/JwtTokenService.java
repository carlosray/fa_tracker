package ml.ipvz.fa.authservice.service.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ml.ipvz.fa.authservice.model.config.TokenConfig;
import ml.ipvz.fa.authservice.service.TokenService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {
    private final static String ISSUER = "fa-auth-service";

    private final TokenConfig config;
    private final Clock clock;

    @SneakyThrows
    @Override
    public Mono<String> generate(Map<String, Object> claims) {
        Instant now = clock.instant();
        Instant expire = now.plus(config.getValidDuration());

        return Mono.just(Jwts.builder()
                .setIssuer(ISSUER)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expire))
                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes(StandardCharsets.UTF_8))
                .compact());
    }

    @Override
    public Mono<Claims> parse(String token) throws JwtException {
        return Mono.just(token)
                .flatMap(t -> {
                    try {
                        return Mono.just(Jwts.parser()
                                .setSigningKey(config.getSecret().getBytes(StandardCharsets.UTF_8))
                                .parseClaimsJws(token)
                                .getBody());
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}

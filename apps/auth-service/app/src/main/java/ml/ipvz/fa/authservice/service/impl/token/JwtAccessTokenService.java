package ml.ipvz.fa.authservice.service.impl.token;

import lombok.RequiredArgsConstructor;
import ml.ipvz.fa.authservice.base.model.User;
import ml.ipvz.fa.authservice.base.util.JwtUtil;
import ml.ipvz.fa.authservice.model.config.AccessTokenType;
import ml.ipvz.fa.authservice.model.config.TokenConfig;
import ml.ipvz.fa.authservice.service.AccessTokenService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtAccessTokenService implements AccessTokenService {
    private final static String ISSUER = "fa-auth-service";

    private final TokenConfig config;

    @Override
    public Mono<String> generate(User user, AccessTokenType type) {
        return Mono.just(JwtUtil.generate(ISSUER, user, config.getValidDuration(), config.getKey(type)));
    }

    @Override
    public Mono<User> parse(String token, AccessTokenType type) {
        return Mono.just(token)
                .flatMap(t -> {
                    try {
                        return Mono.just(JwtUtil.parse(token, config.getKey(type)));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}

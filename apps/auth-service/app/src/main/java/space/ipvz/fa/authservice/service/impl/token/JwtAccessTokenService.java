package space.ipvz.fa.authservice.service.impl.token;

import lombok.RequiredArgsConstructor;
import space.ipvz.fa.authservice.base.model.User;
import space.ipvz.fa.authservice.base.util.JwtUtils;
import space.ipvz.fa.authservice.model.config.AccessTokenType;
import space.ipvz.fa.authservice.model.config.TokenConfig;
import space.ipvz.fa.authservice.service.AccessTokenService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JwtAccessTokenService implements AccessTokenService {
    private final static String ISSUER = "fa-auth-service";

    private final TokenConfig config;

    @Override
    public Mono<String> generate(User user, AccessTokenType type) {
        return Mono.just(JwtUtils.generate(ISSUER, user, config.getValidDuration(), config.getKey(type)));
    }

    @Override
    public Mono<User> parse(String token, AccessTokenType type) {
        return Mono.just(token)
                .flatMap(t -> {
                    try {
                        return Mono.just(JwtUtils.parse(token, config.getKey(type)));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}

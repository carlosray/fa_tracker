package ml.ipvz.fa.authservice.service;

import ml.ipvz.fa.authservice.base.model.User;
import ml.ipvz.fa.authservice.model.config.AccessTokenType;
import reactor.core.publisher.Mono;

public interface AccessTokenService {
    Mono<String> generate(User user, AccessTokenType type);
    Mono<User> parse(String token, AccessTokenType type);
}

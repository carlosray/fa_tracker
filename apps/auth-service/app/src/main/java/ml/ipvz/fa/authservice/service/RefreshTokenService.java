package ml.ipvz.fa.authservice.service;

import ml.ipvz.fa.authservice.base.model.User;
import ml.ipvz.fa.authservice.model.entity.ClientEntity;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {
    Mono<String> generate(User user);
    void validate(String token, ClientEntity client) throws RuntimeException;
}

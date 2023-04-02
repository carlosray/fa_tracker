package space.ipvz.fa.authservice.service;

import space.ipvz.fa.authservice.base.model.User;
import space.ipvz.fa.authservice.model.entity.ClientEntity;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {
    Mono<String> generate(User user);
    void validate(String token, ClientEntity client) throws RuntimeException;
}

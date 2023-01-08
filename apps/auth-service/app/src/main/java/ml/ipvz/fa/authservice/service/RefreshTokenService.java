package ml.ipvz.fa.authservice.service;

import reactor.core.publisher.Mono;

public interface RefreshTokenService {
    Mono<String> generate(int length);
}

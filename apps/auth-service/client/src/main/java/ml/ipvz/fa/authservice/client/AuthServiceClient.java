package ml.ipvz.fa.authservice.client;

import ml.ipvz.fa.authservice.model.token.AccessToken;
import reactor.core.publisher.Mono;

public interface AuthServiceClient {
    Mono<AccessToken> check(String token);
}

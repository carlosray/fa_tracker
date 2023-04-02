package space.ipvz.fa.authservice.client;

import space.ipvz.fa.authservice.model.token.AccessToken;
import reactor.core.publisher.Mono;

public interface AuthServiceClient {
    Mono<AccessToken> check(String token);
}

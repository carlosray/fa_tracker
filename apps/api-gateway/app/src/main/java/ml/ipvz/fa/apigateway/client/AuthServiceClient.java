package ml.ipvz.fa.apigateway.client;

import ml.ipvz.fa.apigateway.model.Token;
import reactor.core.publisher.Mono;

public interface AuthServiceClient {
    Mono<Token> check(String token);
}

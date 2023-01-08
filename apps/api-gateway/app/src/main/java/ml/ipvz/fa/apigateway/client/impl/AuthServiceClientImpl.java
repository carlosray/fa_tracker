package ml.ipvz.fa.apigateway.client.impl;

import java.time.Duration;

import ml.ipvz.fa.apigateway.client.AuthServiceClient;
import ml.ipvz.fa.apigateway.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceClientImpl implements AuthServiceClient {

    private final WebClient authServiceClient;

    @Override
    public Mono<Token> check(String token) {
        return authServiceClient.get()
                .uri("/auth/check")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Token.class)
                .timeout(Duration.ofSeconds(30));
    }
}

package ml.ipvz.fa.authservice.client.impl;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import ml.ipvz.fa.authservice.client.AuthServiceClient;
import ml.ipvz.fa.authservice.model.token.AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceClientImpl implements AuthServiceClient {

    private final WebClient authServiceClient;

    @Override
    public Mono<AccessToken> check(String token) {
        return authServiceClient.get()
                .uri("/auth/check")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(AccessToken.class)
                .timeout(Duration.ofSeconds(30));
    }
}

package ml.ipvz.fa.apigateway.filter.auth;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ml.ipvz.fa.authservice.client.AuthServiceClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private final static String FILTER_NAME = "Auth";
    private final AuthServiceClient authServiceClient;

    @Override
    public String name() {
        return FILTER_NAME;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            HttpHeaders headers = exchange.getRequest().getHeaders();
            List<String> authHeader = headers.getOrEmpty(HttpHeaders.AUTHORIZATION);

            if (authHeader.isEmpty() || authHeader.get(0) == null || authHeader.get(0).isBlank()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "There is no token provided");
            }

            return authServiceClient.check(authHeader.get(0))
                    .doOnError(e -> e instanceof WebClientResponseException, e -> {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "There is no token provided");
                    })
                    .flatMap(t -> {
                        ServerHttpRequest request = exchange.getRequest()
                                .mutate()
                                .headers((httpHeaders) -> httpHeaders.setBearerAuth(t.accessToken()))
                                .build();
                        return chain.filter(exchange.mutate().request(request).build());
                    });
        };
    }
}

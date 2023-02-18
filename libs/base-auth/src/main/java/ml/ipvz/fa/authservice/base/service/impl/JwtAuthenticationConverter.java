package ml.ipvz.fa.authservice.base.service.impl;

import ml.ipvz.fa.authservice.base.util.JwtUtils;
import ml.ipvz.fa.authservice.base.util.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationConverter {
    private final byte[] key;

    public JwtAuthenticationConverter(byte[] key) {
        this.key = key;
    }

    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return TokenUtils.parseToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(token -> JwtUtils.validate(token, key))
                .map(token -> JwtUtils.getAuthentication(token, key));
    }

}


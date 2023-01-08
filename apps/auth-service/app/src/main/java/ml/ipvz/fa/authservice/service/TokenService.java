package ml.ipvz.fa.authservice.service;

import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<String> generate(Map<String, Object> claims);

    Mono<Claims> parse(String token) throws JwtException;
}

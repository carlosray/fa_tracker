package ml.ipvz.fa.authservice.base.util;

import reactor.core.publisher.Mono;

public class TokenUtils {
    private TokenUtils() {
    }

    public static Mono<String> parseToken(String authHeader) {
        return Mono.justOrEmpty(authHeader).map(h -> h.startsWith("Bearer ") ? h.substring(7) : h);
    }
}

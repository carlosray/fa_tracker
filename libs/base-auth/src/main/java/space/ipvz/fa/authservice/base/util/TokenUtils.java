package space.ipvz.fa.authservice.base.util;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TokenUtils {
    private TokenUtils() {
    }

    public static Mono<String> parseToken(String authHeader) {
        return Mono.justOrEmpty(authHeader).map(h -> h.startsWith("Bearer ") ? h.substring(7) : h);
    }

    public static Mono<String> toAuthHeader(String token) {
        return Mono.justOrEmpty(token).map(t -> t.startsWith("Bearer ") ? t : "Bearer " + t);
    }

    public static Mono<String> authHeaderFromContextOrDefault() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(c -> c.getAuthentication().getCredentials())
                .flatMap(c -> toAuthHeader(Objects.toString(c)))
                .defaultIfEmpty("null");
    }

    public static <T> Flux<T> withAuthContextFlux(Function<String, Flux<T>> function) {
        return authHeaderFromContextOrDefault().flatMapMany(function);
    }

    public static <T> Mono<T> withAuthContextMono(Function<String, Mono<T>> function) {
        return authHeaderFromContextOrDefault().flatMap(function);
    }

}

package space.ipvz.fa.authservice.base.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;
import space.ipvz.fa.authservice.base.model.User;

public class SecurityContextUtils {
    private SecurityContextUtils() {
    }

    public static Mono<String> getUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(c -> Optional.ofNullable(c.getAuthentication())
                        .map(Authentication::getPrincipal)
                        .filter(p -> p instanceof User)
                        .map(p -> ((User) p).login())
                        .orElse(null));
    }

    public static Mono<Authentication> getAuthentication() {
        return ReactiveSecurityContextHolder.getContext().mapNotNull(SecurityContext::getAuthentication);
    }

    public static Mono<Object> getCredentials() {
        return getAuthentication().mapNotNull(Authentication::getCredentials);
    }
}

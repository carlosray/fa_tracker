package space.ipvz.fa.authservice.base.permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.ipvz.fa.authservice.base.service.PermissionChecker;
import space.ipvz.fa.authservice.base.util.SecurityContextUtils;

public class BasePermissionChecker implements PermissionChecker {
    private static final Logger log = LoggerFactory.getLogger(BasePermissionChecker.class);

    @Override
    public Mono<Boolean> check(Permission permission) {
        return getPermissions().any(p -> p.hasPermission(permission));
    }

    private Flux<Permission> getPermissions() {
        return SecurityContextUtils.getAuthentication()
                .flatMapMany(authentication -> Flux.fromIterable(authentication.getAuthorities()))
                .mapNotNull(this::toPermission);
    }

    private @Nullable Permission toPermission(GrantedAuthority grantedAuthority) {
        try {
            return Permission.fromString(grantedAuthority.getAuthority());
        } catch (Exception e) {
            log.warn("Error while parsing authorities", e);
            return null;
        }
    }
}

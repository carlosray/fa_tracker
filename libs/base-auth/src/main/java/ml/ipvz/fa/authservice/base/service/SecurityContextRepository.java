package ml.ipvz.fa.authservice.base.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationConverter converter;

    public SecurityContextRepository(JwtAuthenticationConverter converter) {
        this.converter = converter;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        return converter.convert(serverWebExchange).map(SecurityContextImpl::new);
    }
}

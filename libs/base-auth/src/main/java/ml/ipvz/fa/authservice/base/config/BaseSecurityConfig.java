package ml.ipvz.fa.authservice.base.config;

import ml.ipvz.fa.authservice.base.permission.BasePermissionChecker;
import ml.ipvz.fa.authservice.base.permission.aspect.PermissionAspect;
import ml.ipvz.fa.authservice.base.service.PermissionChecker;
import ml.ipvz.fa.authservice.base.service.impl.JwtAuthenticationConverter;
import ml.ipvz.fa.authservice.base.service.impl.SecurityContextRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableAspectJAutoProxy
public class BaseSecurityConfig {

    @Bean
    public ServerSecurityContextRepository serverSecurityContextRepository(@Value("${token.internal-key}") byte[] key) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter(key);
        return new SecurityContextRepository(converter);
    }

    @Bean
    public PermissionChecker permissionChecker() {
        return new BasePermissionChecker();
    }

    @Bean
    public PermissionAspect permissionAspect(PermissionChecker checker) {
        return new PermissionAspect(checker);
    }
}

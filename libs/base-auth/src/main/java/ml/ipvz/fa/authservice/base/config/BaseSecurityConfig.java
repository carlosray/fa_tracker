package ml.ipvz.fa.authservice.base.config;

import ml.ipvz.fa.authservice.base.service.JwtAuthenticationConverter;
import ml.ipvz.fa.authservice.base.service.SecurityContextRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class BaseSecurityConfig {

    @Bean
    public ServerSecurityContextRepository serverSecurityContextRepository(@Value("${token.internal-key}") byte[] key) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter(key);
        return new SecurityContextRepository(converter);
    }
}

package space.ipvz.fa.userservice.config

import space.ipvz.fa.authservice.base.config.BaseSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository

@Configuration
@Import(BaseSecurityConfig::class)
class SecurityConfig(
    private val serverSecurityContextRepository: ServerSecurityContextRepository
) {

    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .logout().disable()

            .securityContextRepository(serverSecurityContextRepository)

            .authorizeExchange()
            .pathMatchers("/actuator/health/**").permitAll() //health
            .pathMatchers(HttpMethod.POST, "/users/login").permitAll() //login
            .pathMatchers(HttpMethod.POST, "/users").permitAll() //register
            .pathMatchers(HttpMethod.GET, "/users/permissions/*").permitAll() //permissions
            .anyExchange().authenticated()

            .and()
            .build()
    }
}
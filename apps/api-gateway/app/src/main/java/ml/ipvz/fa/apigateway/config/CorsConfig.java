package ml.ipvz.fa.apigateway.config;

import java.util.Optional;

import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig {

    private static final String ALLOWED_ALL = "*";
    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter(GlobalCorsProperties corsProperties) {
        CorsConfiguration corsConfiguration = corsProperties.getCorsConfigurations().get("/**");
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                        Optional.ofNullable(corsConfiguration.getAllowedOrigins())
                                .map(h -> String.join(", ", h))
                                .orElse(ALLOWED_ALL)
                );
                headers.add(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                        Optional.ofNullable(corsConfiguration.getAllowedMethods())
                                .map(h -> String.join(", ", h))
                                .orElse(ALLOWED_ALL)
                );
                headers.add(
                        HttpHeaders.ACCESS_CONTROL_MAX_AGE,
                        Optional.ofNullable(corsConfiguration.getMaxAge()).map(Object::toString).orElse(MAX_AGE)
                );
                headers.add(
                        HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                        Optional.ofNullable(corsConfiguration.getAllowedHeaders())
                                .map(h -> String.join(", ", h))
                                .orElse(ALLOWED_ALL)
                );
                if (CorsUtils.isPreFlightRequest(request)) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}

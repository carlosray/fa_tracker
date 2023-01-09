package ml.ipvz.fa.authservice.config;

import ml.ipvz.fa.authservice.client.AuthServiceClient;
import ml.ipvz.fa.authservice.client.impl.AuthServiceClientImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AuthClientConfiguration {
    @Bean("authServiceWebClient")
    public WebClient authServiceWebClient(@Value("${client.auth-service.uri}") String uri) {
        return WebClient.builder()
                .baseUrl(uri)
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder());
                    clientCodecConfigurer.customCodecs().register(new Jackson2JsonEncoder());
                })
                .build();
    }

    @Bean
    public AuthServiceClient authServiceClient(@Qualifier("authServiceWebClient") WebClient authServiceWebClient) {
        return new AuthServiceClientImpl(authServiceWebClient);
    }


}

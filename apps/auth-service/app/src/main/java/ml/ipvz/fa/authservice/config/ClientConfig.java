package ml.ipvz.fa.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ClientConfig {

    @Bean
    public Decoder<Object> decoder() {
        return new Jackson2JsonDecoder();
    }

    @Bean
    public Encoder<Object> encoder() {
        return new Jackson2JsonEncoder();
    }

    @Bean("userServiceClient")
    public WebClient userServiceClient(@Value("${client.user-service.uri}") String url) {
        HttpClient httpClient = HttpClient.create();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(url)
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.customCodecs().register(decoder());
                    clientCodecConfigurer.customCodecs().register(encoder());
                })
                .build();
    }

}


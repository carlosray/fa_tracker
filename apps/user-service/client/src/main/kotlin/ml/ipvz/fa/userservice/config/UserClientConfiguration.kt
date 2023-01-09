package ml.ipvz.fa.userservice.config

import ml.ipvz.fa.userservice.client.UserServiceClient
import ml.ipvz.fa.userservice.client.impl.UserServiceClientImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class UserClientConfiguration {

    @Bean("userServiceWebClient")
    fun userServiceWebClient(@Value("\${client.user-service.uri}") uri: String): WebClient {
        return WebClient.builder()
            .baseUrl(uri)
            .codecs { clientCodecConfigurer: ClientCodecConfigurer ->
                clientCodecConfigurer.customCodecs().register(Jackson2JsonDecoder())
                clientCodecConfigurer.customCodecs().register(Jackson2JsonEncoder())
            }
            .build()
    }

    @Bean
    fun userServiceClient(@Qualifier("userServiceWebClient") userServiceWebClient: WebClient): UserServiceClient =
        UserServiceClientImpl(userServiceWebClient)
}
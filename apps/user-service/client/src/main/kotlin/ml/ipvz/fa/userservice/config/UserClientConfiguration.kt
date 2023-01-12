package ml.ipvz.fa.userservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
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
    fun userServiceWebClient(@Value("\${client.user-service.uri}") uri: String, mapper: ObjectMapper): WebClient {
        val mapperWithModule = mapper.registerKotlinModule()
        return WebClient.builder()
            .baseUrl(uri)
            .codecs { clientCodecConfigurer: ClientCodecConfigurer ->
                clientCodecConfigurer.customCodecs().register(Jackson2JsonDecoder(mapperWithModule))
                clientCodecConfigurer.customCodecs().register(Jackson2JsonEncoder(mapperWithModule))
            }
            .build()
    }

    @Bean
    fun userServiceClient(@Qualifier("userServiceWebClient") userServiceWebClient: WebClient): UserServiceClient =
        UserServiceClientImpl(userServiceWebClient)
}
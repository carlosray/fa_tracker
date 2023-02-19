package ml.ipvz.fa.operationservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ml.ipvz.fa.operationservice.client.OperationServiceClient
import ml.ipvz.fa.operationservice.client.impl.OperationServiceClientImpl
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
class OperationClientConfiguration {

    @Bean("operationServiceWebClient")
    fun operationServiceWebClient(
        @Value("\${client.operation-service.uri}") uri: String,
        mapper: ObjectMapper
    ): WebClient {
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
    fun operationServiceClient(@Qualifier("operationServiceWebClient") operationServiceWebClient: WebClient): OperationServiceClient =
        OperationServiceClientImpl(operationServiceWebClient, Duration.ofSeconds(30))
}
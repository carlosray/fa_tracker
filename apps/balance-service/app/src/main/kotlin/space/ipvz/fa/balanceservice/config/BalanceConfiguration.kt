package space.ipvz.fa.balanceservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.reactive.function.client.WebClient

@EnableScheduling
@Configuration
class BalanceConfiguration {
    @Bean("fixerWebClient")
    fun fixerApiClient(
        @Value("\${currency.fixer.uri}") uri: String,
        @Value("\${currency.fixer.apiKey}") apiKey: String,
        mapper: ObjectMapper
    ): WebClient {
        val mapperWithModule = mapper.registerKotlinModule()
        return WebClient.builder()
            .baseUrl(uri)
            .codecs { clientCodecConfigurer: ClientCodecConfigurer ->
                clientCodecConfigurer.customCodecs().register(Jackson2JsonDecoder(mapperWithModule))
                clientCodecConfigurer.customCodecs().register(Jackson2JsonEncoder(mapperWithModule))
            }
            .defaultHeader("apikey", apiKey)
            .build()
    }
}
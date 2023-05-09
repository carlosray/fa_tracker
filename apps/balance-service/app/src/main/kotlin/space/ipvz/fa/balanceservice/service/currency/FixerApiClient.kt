package space.ipvz.fa.balanceservice.service.currency

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import space.ipvz.fa.balanceservice.logging.LoggerDelegate
import space.ipvz.fa.balanceservice.model.entity.CurrencyRates
import space.ipvz.fa.cloud.model.Currency
import java.time.Duration

@Service
class FixerApiClient(
    @Qualifier("fixerWebClient")
    private val webClient: WebClient
) : CurrencyClient {

    override fun getRates(currencies: Set<Currency>, base: Currency): Mono<CurrencyRates> {
        val c = currencies.joinToString(",")
        return webClient.get()
            .uri("/latest?symbols=$c&base=$base")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response -> response.createError() }
            .bodyToMono(CurrencyRates::class.java)
            .timeout(Duration.ofSeconds(30))
    }
}
package space.ipvz.fa.balanceservice.service.currency

import reactor.core.publisher.Mono
import space.ipvz.fa.balanceservice.model.entity.CurrencyRates
import space.ipvz.fa.cloud.model.Currency

interface CurrencyClient {
    fun getRates(currencies: Set<Currency>, base: Currency): Mono<CurrencyRates>
}
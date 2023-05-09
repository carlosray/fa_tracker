package space.ipvz.fa.balanceservice.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import space.ipvz.fa.balanceservice.model.entity.CurrencyRates
import space.ipvz.fa.balanceservice.service.CurrencyService
import space.ipvz.fa.balanceservice.service.currency.CurrencyClient
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import java.math.BigDecimal
import java.time.Duration
import java.time.Instant

@Service
class CurrencyServiceImpl(
    currencyClient: CurrencyClient
) : CurrencyService {

    @Value("\${currency.cache}")
    private lateinit var currencyCache: Duration

    private var rates: Lazy<Mono<CurrencyRates>> = lazy {
        currencyClient.getRates(Currency.values().toSet(), Currency.USD)
            .cache(currencyCache)
    }

    override fun applyCurrency(balance: Balance, to: Currency): Mono<Balance> =
        applyCurrency(balance.amount, balance.currency, to).map { Balance(it, to, Instant.now()) }

    override fun applyCurrency(amount: BigDecimal, from: Currency, to: Currency): Mono<BigDecimal> {
        if (from == to) {
            return Mono.just(amount)
        }

        return rates.value.map { amount / it.rates[from]!! * it.rates[to]!! }
    }
}
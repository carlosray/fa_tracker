package space.ipvz.fa.balanceservice.service;

import reactor.core.publisher.Mono
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import java.math.BigDecimal

interface CurrencyService {
    fun applyCurrency(balance: Balance, to: Currency): Mono<Balance>
    fun applyCurrency(amount: BigDecimal, from: Currency, to: Currency): Mono<BigDecimal>
}

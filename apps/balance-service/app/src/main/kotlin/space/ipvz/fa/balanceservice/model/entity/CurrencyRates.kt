package space.ipvz.fa.balanceservice.model.entity

import space.ipvz.fa.cloud.model.Currency
import java.math.BigDecimal
import java.time.Instant

data class CurrencyRates(
    val timestamp: Instant,
    val base: Currency,
    val rates: Map<Currency, BigDecimal>
)

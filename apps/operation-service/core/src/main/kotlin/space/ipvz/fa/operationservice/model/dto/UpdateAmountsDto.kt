package space.ipvz.fa.operationservice.model.dto

import java.math.BigDecimal
import java.time.Instant

data class UpdateAmountsDto(
    val accounts: Collection<Amount>,
) {
    data class Amount(
        val id: Long,
        val groupId: Long,
        val amount: BigDecimal,
        val lastUpdate: Instant
    )
}
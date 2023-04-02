package space.ipvz.fa.balanceservice.model.entity

import space.ipvz.fa.cloud.model.Currency
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("balance")
data class BalanceEntity(
    @Id
    val id: Long? = null,
    val groupId: Long,
    val entityId: Long,
    val currency: Currency,
    val amount: BigDecimal,
    @LastModifiedDate
    var modified: Instant = Instant.now(),
)
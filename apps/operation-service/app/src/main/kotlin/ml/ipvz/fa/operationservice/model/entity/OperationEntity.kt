package ml.ipvz.fa.operationservice.model.entity

import ml.ipvz.fa.operationservice.model.Currency
import java.math.BigDecimal
import java.time.Instant

data class OperationEntity(
    val id: Long? = null,
    val name: String,
    val description: String,
    val amount: BigDecimal,
    val currency: Currency,
    var created: Instant = Instant.now(),
    var createdBy: String = "default",
    var modified: Instant = Instant.now(),
    var modifiedBy: String = "default"
)
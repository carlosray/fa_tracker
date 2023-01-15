package ml.ipvz.fa.operationservice.model.dto

import java.time.Instant

data class OperationEventDto(
    val id: String?,
    val groupId: Long,
    val description: String,
    val detail: Operation,
    val created: Instant?,
    val createdBy: String?,
    val modified: Instant?,
    val modifiedBy: String?,
)


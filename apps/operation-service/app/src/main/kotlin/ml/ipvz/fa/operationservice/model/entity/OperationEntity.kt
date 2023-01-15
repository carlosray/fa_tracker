package ml.ipvz.fa.operationservice.model.entity

import ml.ipvz.fa.operationservice.model.dto.Operation
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("operation")
data class OperationEntity(
    @Id
    val id: String? = null,
    val groupId: Long,
    val description: String,
    val detail: Operation,
    var created: Instant,
    var createdBy: String,
    var modified: Instant,
    var modifiedBy: String
)
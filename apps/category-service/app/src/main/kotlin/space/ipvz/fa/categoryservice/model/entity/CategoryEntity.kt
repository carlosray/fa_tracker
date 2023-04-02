package space.ipvz.fa.categoryservice.model.entity

import space.ipvz.fa.cloud.model.OperationType
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("category")
data class CategoryEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val config: CategoryConfig,
    val type: OperationType,
    val groupId: Long,
    @CreatedDate
    var created: Instant = Instant.now(),
    @CreatedBy
    var createdBy: String = "default",
    @LastModifiedDate
    var modified: Instant = Instant.now(),
    @LastModifiedBy
    var modifiedBy: String = "default"
)
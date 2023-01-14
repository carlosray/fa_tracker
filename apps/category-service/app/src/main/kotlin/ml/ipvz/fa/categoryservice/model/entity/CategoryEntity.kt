package ml.ipvz.fa.categoryservice.model.entity

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
    val groupId: Long,
    @CreatedDate
    var created: Instant = Instant.now(),
    @LastModifiedDate
    var modified: Instant = Instant.now(),
    @LastModifiedBy
    var modifiedBy: String = "default"
)
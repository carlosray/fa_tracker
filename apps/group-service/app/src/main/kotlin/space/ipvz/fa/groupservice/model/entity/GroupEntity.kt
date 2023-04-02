package space.ipvz.fa.groupservice.model.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("\"group\"")
data class GroupEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val config: GroupConfig,
    val description: String,
    @CreatedDate
    var created: Instant = Instant.now(),
    @CreatedBy
    var createdBy: String = "default",
    @LastModifiedDate
    var modified: Instant = Instant.now(),
    @LastModifiedBy
    var modifiedBy: String = "default"
)
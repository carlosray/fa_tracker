package space.ipvz.fa.userservice.model.entity;

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("\"role\"")
data class RoleEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val permission: String,
    @CreatedDate
    val created: Instant = Instant.now()
)

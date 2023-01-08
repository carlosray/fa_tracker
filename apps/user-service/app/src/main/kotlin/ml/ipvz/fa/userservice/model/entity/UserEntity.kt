package ml.ipvz.fa.userservice.model.entity;

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("\"user\"")
data class UserEntity(
    @Id
    val id: Long?,
    val login: String,
    val email: String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val deleted: Instant?,
    @CreatedDate
    val created: Instant
)

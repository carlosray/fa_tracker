package ml.ipvz.fa.userservice.model;

import ml.ipvz.fa.authservice.base.permission.Permission
import java.time.Instant;

data class UserDto(
    val id: Long,
    val login: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val created: Instant,
    val permissions: List<Permission>
)

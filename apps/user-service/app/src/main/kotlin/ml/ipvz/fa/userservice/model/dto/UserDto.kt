package ml.ipvz.fa.userservice.model.dto;

import java.time.Instant;

data class UserDto(
    val id: Long,
    val login: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val created: Instant
)

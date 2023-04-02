package space.ipvz.fa.userservice.model;

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterDto(
    @field:NotBlank
    val login: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    @field:Size(min = 8)
    val password: String,
    val firstName: String?,
    val lastName: String?
)

package ml.ipvz.fa.userservice.model

import jakarta.validation.constraints.NotBlank

data class LoginDto(
    @field:NotBlank
    val login: String,
    @field:NotBlank
    val password: String
)

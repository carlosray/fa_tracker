package ml.ipvz.fa.userservice.model.dto;

import jakarta.validation.constraints.NotBlank

data class AuthDto(
    @field:NotBlank
    val login: String,
    @field:NotBlank
    val password: String
)

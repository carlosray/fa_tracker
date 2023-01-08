package ml.ipvz.fa.authservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}

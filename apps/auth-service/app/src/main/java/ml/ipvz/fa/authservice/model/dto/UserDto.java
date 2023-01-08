package ml.ipvz.fa.authservice.model.dto;

import java.time.Instant;

public record UserDto(
        Long id,
        String login,
        String email,
        String firstName,
        String lastName,
        Instant created
) {
}

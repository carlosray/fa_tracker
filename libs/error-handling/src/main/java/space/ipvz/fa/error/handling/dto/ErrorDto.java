package space.ipvz.fa.error.handling.dto;

import java.time.Instant;

public record ErrorDto(
        Integer status,
        String message,
        Instant timestamp
) {
}

package space.ipvz.fa.cloud.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Balance(
        BigDecimal amount,
        Currency currency,
        Instant lastUpdate
) {
    public static Balance empty(Currency currency) {
        return new Balance(BigDecimal.ZERO, currency, Instant.now());
    }
}

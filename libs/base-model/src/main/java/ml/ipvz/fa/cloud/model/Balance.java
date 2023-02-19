package ml.ipvz.fa.cloud.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Balance(
        BigDecimal amount,
        Currency currency,
        Instant lastUpdate
) {
}

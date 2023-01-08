package ml.ipvz.fa.authservice.model.config;

import java.time.Duration;

import lombok.Data;

@Data
public class TokenConfig {
    String secret;
    Duration validDuration;
    Duration refreshDuration;
    Integer refreshLength;
}

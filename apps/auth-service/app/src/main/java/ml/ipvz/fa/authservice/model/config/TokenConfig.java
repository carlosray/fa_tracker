package ml.ipvz.fa.authservice.model.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import lombok.Data;

@Data
public class TokenConfig {
    String externalKey;
    String internalKey;
    Duration validDuration;
    Duration refreshDuration;
    Integer refreshLength;

    public byte[] getKey(AccessTokenType type) {
        String key = switch (type) {
            case EXTERNAL -> externalKey;
            case INTERNAL -> internalKey;
        };

        return key.getBytes(StandardCharsets.UTF_8);
    }
}

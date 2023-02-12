package ml.ipvz.fa.authservice.base.model;

import java.util.List;

public record User(
        Long id,
        String login,
        List<String> roles
) {
}

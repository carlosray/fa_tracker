package space.ipvz.fa.authservice.model.entity;


import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("client")
public record ClientEntity(
        @Id
        String id,
        String refreshToken,
        Instant updated
) {
}

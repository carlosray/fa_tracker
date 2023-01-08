package ml.ipvz.fa.authservice.repository;

import java.time.Instant;

import ml.ipvz.fa.authservice.model.entity.ClientEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthClientRepository extends R2dbcRepository<ClientEntity, String> {
    @Modifying
    @Query("INSERT INTO client(id, refresh_token, updated) VALUES (:id, :refreshToken, :updated)")
    Mono<Void> insert(@Param("id") String id,
                              @Param("refreshToken") String refreshToken,
                              @Param("updated") Instant updated);

}

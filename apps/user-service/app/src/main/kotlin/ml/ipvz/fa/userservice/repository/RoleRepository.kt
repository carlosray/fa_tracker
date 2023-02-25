package ml.ipvz.fa.userservice.repository;

import ml.ipvz.fa.userservice.model.entity.RoleEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface RoleRepository : R2dbcRepository<RoleEntity, Long> {
    fun findByUserId(userId: Long): Flux<RoleEntity>
    fun deleteByUserIdAndPermission(userId: Long, permission: String): Mono<Void>
}

package space.ipvz.fa.userservice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.userservice.model.entity.RoleEntity

@Repository
interface RoleRepository : R2dbcRepository<RoleEntity, Long> {
    fun findByUserId(userId: Long): Flux<RoleEntity>
    fun deleteByUserIdAndPermission(userId: Long, permission: String): Mono<Void>
    fun findAllByPermissionStartsWith(permissionPrefix: String): Flux<RoleEntity>
}

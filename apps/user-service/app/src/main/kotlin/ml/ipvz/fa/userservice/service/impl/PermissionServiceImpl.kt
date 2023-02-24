package ml.ipvz.fa.userservice.service.impl;

import ml.ipvz.fa.authservice.base.exception.AccessForbiddenException
import ml.ipvz.fa.authservice.base.permission.Permission
import ml.ipvz.fa.authservice.base.service.PermissionChecker
import ml.ipvz.fa.userservice.logging.LoggerDelegate
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.model.entity.RoleEntity
import ml.ipvz.fa.userservice.repository.RoleRepository
import ml.ipvz.fa.userservice.service.PermissionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PermissionServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionChecker: PermissionChecker
) : PermissionService {
    private val log by LoggerDelegate()

    override fun findByUser(userId: Long): Flux<Permission> =
        roleRepository.findByUserId(userId)
            .map { Permission.fromString(it.permission) }

    override fun updatePermissions(userId: Long, update: UpdatePermissionsDto): Mono<Void> =
        checkCurrentUserHasPermissionsToManageRoles(update)
            .then(update(userId, update))

    private fun update(userId: Long, update: UpdatePermissionsDto): Mono<Void> {
        var result: Flux<Any> = Flux.empty()
        if (update.delete.isNotEmpty()) {
            result = result.concatWith(
                roleRepository.deleteByUserIdAndPermissionIn(
                    userId,
                    update.delete.map { it.toString() })
            )
        }

        if (update.add.isNotEmpty()) {
            val roleEntities = update.add.map { RoleEntity(userId = userId, permission = it.toString()) }
            result = result.concatWith(roleRepository.saveAll(roleEntities)
                .doOnNext { log.info("Given permission ${it.permission} to user ${it.userId}") }
            )
        }

        return result.then()
    }

    private fun checkCurrentUserHasPermissionsToManageRoles(update: UpdatePermissionsDto): Mono<Permission> =
        Flux.fromIterable(update.add).concatWith(Flux.fromIterable(update.delete))
            .filterWhen { permissionChecker.check(it).map { b -> !b } }
            .next()
            .flatMap { Mono.error { AccessForbiddenException(it) } }
}

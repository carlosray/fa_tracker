package space.ipvz.fa.userservice.service.impl;

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import space.ipvz.fa.async.model.event.PermissionUpdatedEvent
import space.ipvz.fa.async.service.send
import space.ipvz.fa.authservice.base.exception.AccessForbiddenException
import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.authservice.base.service.PermissionChecker
import space.ipvz.fa.userservice.logging.LoggerDelegate
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import space.ipvz.fa.userservice.model.UpdatePermissionsDto.Action.DELETE
import space.ipvz.fa.userservice.model.UpdatePermissionsDto.Action.GRANT
import space.ipvz.fa.userservice.model.entity.RoleEntity
import space.ipvz.fa.userservice.repository.RoleRepository
import space.ipvz.fa.userservice.service.PermissionService

@Service
class PermissionServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionChecker: PermissionChecker
) : PermissionService {
    private val log by LoggerDelegate()

    override fun findByUser(userId: Long): Flux<Permission> =
        roleRepository.findByUserId(userId)
            .map { Permission.fromString(it.permission) }

    override fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<Void> =
        updates.toFlux()
            .flatMap {
                when (it.action) {
                    GRANT -> roleRepository.save(RoleEntity(userId = it.userId, permission = it.permission.toString()))
                        .doOnNext { e -> log.info("Given permission ${e.permission} to user ${e.userId}") }
                        .map { _ -> it }

                    DELETE -> roleRepository.deleteByUserIdAndPermission(it.userId, it.permission.toString())
                        .map { _ -> it }
                }
            }
            .flatMap { PermissionUpdatedEvent(it.permission.toString(), it.action.isGrant(), it.userId).send() }
            .then()

    override fun checkAll(permissions: Flux<Permission>): Mono<Void> =
        permissions.filterWhen { permissionChecker.check(it).map { b -> !b } }
            .next()
            .flatMap { Mono.error { AccessForbiddenException(it) } }

    override fun deleteByGroupId(groupId: Long): Mono<Void> =
        roleRepository.findAllByPermissionStartsWith("${groupId}.")
            .doOnNext { log.info("Deleted permission ${it.permission} of user ${it.userId}") }
            .flatMap { role -> roleRepository.deleteById(role.id!!)
                .then(PermissionUpdatedEvent(role.permission, false, role.userId).send()) }
            .then()
}

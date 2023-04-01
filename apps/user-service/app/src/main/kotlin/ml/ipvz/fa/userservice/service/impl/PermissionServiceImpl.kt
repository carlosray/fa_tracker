package ml.ipvz.fa.userservice.service.impl;

import ml.ipvz.fa.authservice.base.exception.AccessForbiddenException
import ml.ipvz.fa.authservice.base.permission.Permission
import ml.ipvz.fa.authservice.base.service.PermissionChecker
import ml.ipvz.fa.cloud.async.model.event.PermissionUpdatedEvent
import ml.ipvz.fa.cloud.async.service.EventService
import ml.ipvz.fa.userservice.logging.LoggerDelegate
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto.Action.DELETE
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto.Action.GRANT
import ml.ipvz.fa.userservice.model.entity.RoleEntity
import ml.ipvz.fa.userservice.repository.RoleRepository
import ml.ipvz.fa.userservice.service.PermissionService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class PermissionServiceImpl(
    private val roleRepository: RoleRepository,
    private val permissionChecker: PermissionChecker,
    private val eventService: EventService
) : PermissionService {
    private val log by LoggerDelegate()

    @Cacheable
    override fun findByUser(userId: Long): Flux<Permission> =
        roleRepository.findByUserId(userId)
            .map { Permission.fromString(it.permission) }

    override fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<Void> =
        updates.toFlux().flatMap {
            when (it.action) {
                GRANT -> roleRepository.save(RoleEntity(userId = it.userId, permission = it.permission.toString()))
                    .doOnNext { e -> log.info("Given permission ${e.permission} to user ${e.userId}") }
                    .map { _ -> it }

                DELETE -> roleRepository.deleteByUserIdAndPermission(it.userId, it.permission.toString())
                    .map { _ -> it }
            }
        }.flatMap {
            eventService.user.permissionUpdated.send(
                PermissionUpdatedEvent(it.permission.toString(), it.action.isGrant(), it.userId)
            )
        }.then()

    override fun checkAll(permissions: Flux<Permission>): Mono<Void> =
        permissions.filterWhen { permissionChecker.check(it).map { b -> !b } }
            .next()
            .flatMap { Mono.error { AccessForbiddenException(it) } }
}

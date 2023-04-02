package space.ipvz.fa.userservice.service;

import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PermissionService {
    fun findByUser(userId: Long): Flux<Permission>
    fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<Void>
    fun checkAll(permissions: Flux<Permission>): Mono<Void>
    fun deleteByGroupId(groupId: Long): Mono<Void>
}

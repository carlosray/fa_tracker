package ml.ipvz.fa.userservice.service;

import ml.ipvz.fa.authservice.base.permission.Permission
import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PermissionService {
    fun findByUser(userId: Long): Flux<Permission>
    fun updatePermissions(updates: List<UpdatePermissionsDto>): Mono<Void>
    fun checkAll(permissions: Flux<Permission>): Mono<Void>
    fun deleteByGroupId(groupId: Long): Mono<Void>
}

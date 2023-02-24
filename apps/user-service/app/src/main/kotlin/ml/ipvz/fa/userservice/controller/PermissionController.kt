package ml.ipvz.fa.userservice.controller

import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.service.PermissionService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("users/{userId}/permissions")
class PermissionController(
    private val permissionService: PermissionService
) {

    @PutMapping
    fun updatePermissions(@PathVariable userId: Long, update: UpdatePermissionsDto): Mono<Void> =
        permissionService.updatePermissions(userId, update)
}
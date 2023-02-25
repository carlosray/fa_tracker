package ml.ipvz.fa.userservice.controller

import ml.ipvz.fa.userservice.model.UpdatePermissionsDto
import ml.ipvz.fa.userservice.service.PermissionService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("users/permissions")
class PermissionController(
    private val permissionService: PermissionService,
) {

    @PutMapping
    fun updatePermissions(@RequestBody updates: List<UpdatePermissionsDto>): Mono<Void> =
        permissionService.updatePermissions(updates)
}
package space.ipvz.fa.userservice.controller

import space.ipvz.fa.authservice.base.permission.Permission
import space.ipvz.fa.userservice.model.UpdatePermissionsDto
import space.ipvz.fa.userservice.service.PermissionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("private")
class PrivateController(
    private val permissionService: PermissionService,
) {

    @PutMapping("users/permissions")
    fun updatePermissions(@RequestBody updates: List<UpdatePermissionsDto>): Mono<Void> =
        permissionService.updatePermissions(updates)

    @GetMapping("users/permissions/{userId}")
    fun getPermissions(@PathVariable userId: Long): Flux<Permission> = permissionService.findByUser(userId)
}
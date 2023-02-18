package ml.ipvz.fa.groupservice.controller

import ml.ipvz.fa.authservice.base.model.User
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import ml.ipvz.fa.authservice.base.permission.model.Resource
import ml.ipvz.fa.authservice.base.permission.model.Role
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("groups")
class GroupController {

    @GetMapping("{groupId}")
    @CheckPermission(resource = Resource.GROUP, role = Role.VIEWER, groupIdFieldName = "groupId")
    fun getGroups(@PathVariable groupId: Long, @AuthenticationPrincipal user: User): Flux<Void> = Flux.empty()
}
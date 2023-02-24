package ml.ipvz.fa.groupservice.controller

import ml.ipvz.fa.authservice.base.model.User
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import ml.ipvz.fa.authservice.base.permission.model.Resource
import ml.ipvz.fa.authservice.base.permission.model.Role
import ml.ipvz.fa.groupservice.model.GroupDto
import ml.ipvz.fa.groupservice.service.GroupService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("groups")
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping
    fun getGroups(@AuthenticationPrincipal user: User): Flux<GroupDto> =
        groupService.getGroupsByIds(
            ids = user.permissions.mapTo(HashSet()) { it.groupId }
        )

    @GetMapping("{groupId}")
    @CheckPermission(resource = Resource.GROUP, role = Role.VIEWER, groupId = "#groupId")
    fun getGroup(@PathVariable groupId: Long, @AuthenticationPrincipal user: User): Mono<GroupDto> =
        groupService.getGroupsByIds(setOf(groupId)).toMono()
}
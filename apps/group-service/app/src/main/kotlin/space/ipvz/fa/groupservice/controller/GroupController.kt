package space.ipvz.fa.groupservice.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import space.ipvz.fa.authservice.base.permission.model.Resource
import space.ipvz.fa.authservice.base.permission.model.Role
import space.ipvz.fa.groupservice.model.GroupCreateDto
import space.ipvz.fa.groupservice.model.GroupDto
import space.ipvz.fa.groupservice.model.GroupUpdateDto
import space.ipvz.fa.groupservice.service.GroupService

@RestController
@RequestMapping("groups")
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping
    fun getGroups(@AuthenticationPrincipal user: User): Flux<GroupDto> =
        groupService.get(
            ids = user.permissions.mapTo(HashSet()) { it.groupId }
        )

    @GetMapping("{groupId}")
    @CheckPermission(resource = Resource.GROUP, role = Role.VIEWER, groupId = "#groupId")
    fun getGroup(@PathVariable groupId: Long): Mono<GroupDto> =
        groupService.get(setOf(groupId)).toMono()

    @PostMapping
    fun createGroup(@RequestBody group: GroupCreateDto, @AuthenticationPrincipal user: User): Mono<GroupDto> =
        groupService.create(group, user)

    @PutMapping
    @CheckPermission(resource = Resource.GROUP, role = Role.EDITOR, groupId = "#groupDto.id")
    fun updateGroup(@RequestBody groupDto: GroupUpdateDto): Mono<GroupDto> = groupService.update(groupDto)

    @DeleteMapping("{groupId}")
    @CheckPermission(resource = Resource.GROUP, role = Role.ADMIN, groupId = "#groupId")
    fun deleteGroup(@PathVariable groupId: Long): Mono<Void> = groupService.delete(groupId)
}
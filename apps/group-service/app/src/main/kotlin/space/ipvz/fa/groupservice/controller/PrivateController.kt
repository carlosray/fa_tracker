package space.ipvz.fa.groupservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import space.ipvz.fa.groupservice.model.GroupDto
import space.ipvz.fa.groupservice.service.GroupService

@RestController
@RequestMapping("private")
class PrivateController(
    private val groupService: GroupService
) {

    @GetMapping("groups")
    fun getGroups(
        @RequestParam(required = false) ids: Set<Long>?,
        @RequestParam(required = false, defaultValue = "false") withBalance: Boolean
    ): Flux<GroupDto> =
        groupService.getAll(ids, withBalance)
}
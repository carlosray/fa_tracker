package space.ipvz.fa.operationservice.controller

import space.ipvz.fa.authservice.base.model.User
import space.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import space.ipvz.fa.authservice.base.permission.model.Resource
import space.ipvz.fa.authservice.base.permission.model.Role
import space.ipvz.fa.operationservice.model.dto.OperationEventDto
import space.ipvz.fa.operationservice.service.OperationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("operations")
class OperationController(
    private val operationService: OperationService
) {

    @PostMapping
    @CheckPermission(resource = Resource.OPERATION, role = Role.EDITOR, groupId = "#operation.groupId")
    fun createOperation(@RequestBody operation: OperationEventDto): Mono<OperationEventDto> =
        operationService.create(operation).map { it.toDto() }

    @GetMapping("group/{groupId}")
    @CheckPermission(resource = Resource.OPERATION, role = Role.VIEWER, groupId = "#groupId")
    fun getOperations(@PathVariable groupId: Long, @AuthenticationPrincipal user: User): Flux<OperationEventDto> =
        operationService.getAll(groupId).map { it.toDto() }
}
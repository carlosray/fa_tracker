package ml.ipvz.fa.operationservice.controller

import ml.ipvz.fa.authservice.base.model.User
import ml.ipvz.fa.authservice.base.permission.annotation.CheckPermission
import ml.ipvz.fa.authservice.base.permission.model.Resource
import ml.ipvz.fa.authservice.base.permission.model.Role
import ml.ipvz.fa.operationservice.logging.LoggerDelegate
import ml.ipvz.fa.operationservice.model.dto.OperationEventDto
import ml.ipvz.fa.operationservice.service.OperationService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("operations")
class OperationController(
    private val operationService: OperationService
) {
    private val log by LoggerDelegate()

    @PostMapping
    fun createOperation(@RequestBody operation: Mono<OperationEventDto>): Mono<OperationEventDto> =
        operationService.create(operation).map { it.toDto() }

    @GetMapping
    @CheckPermission(resource = Resource.GROUP, role = Role.VIEWER, targetIdFieldName = "groupId")
    fun getOperations(
        @RequestParam(required = false, name = "groupId") groupId: Long?,
        @AuthenticationPrincipal user: User
    ): Flux<OperationEventDto> =
        (if (groupId != null) operationService.getAllByGroupId(groupId) else operationService.getAll())
            .map { it.toDto() }
}
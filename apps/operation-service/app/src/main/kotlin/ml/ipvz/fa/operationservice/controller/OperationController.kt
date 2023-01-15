package ml.ipvz.fa.operationservice.controller

import ml.ipvz.fa.operationservice.model.dto.OperationEventDto
import ml.ipvz.fa.operationservice.service.OperationService
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

    @PostMapping
    fun createOperation(@RequestBody operation: Mono<OperationEventDto>): Mono<OperationEventDto> =
        operationService.create(operation).map { it.toDto() }

    @GetMapping
    fun getOperations(@RequestParam(required = false, name = "groupId") groupId: Long?): Flux<OperationEventDto> =
        (if (groupId != null) operationService.getAllByGroupId(groupId) else operationService.getAll())
            .map { it.toDto() }
}
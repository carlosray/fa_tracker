package space.ipvz.fa.operationservice.service;

import space.ipvz.fa.operationservice.model.dto.OperationEventDto
import space.ipvz.fa.operationservice.model.entity.OperationEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OperationService {
    fun create(operation: OperationEventDto): Mono<OperationEntity>
    fun getAll(groupId: Long? = null): Flux<OperationEntity>
}

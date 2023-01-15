package ml.ipvz.fa.operationservice.service;

import ml.ipvz.fa.operationservice.model.dto.OperationEventDto
import ml.ipvz.fa.operationservice.model.entity.OperationEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OperationService {
    fun create(operation: Mono<OperationEventDto>): Mono<OperationEntity>
    fun getAll(): Flux<OperationEntity>
    fun getAllByGroupId(groupId: Long): Flux<OperationEntity>
}

package ml.ipvz.fa.operationservice.service.impl

import ml.ipvz.fa.operationservice.exception.IdMustBeNullException
import ml.ipvz.fa.operationservice.logging.LoggerDelegate
import ml.ipvz.fa.operationservice.model.dto.OperationEventDto
import ml.ipvz.fa.operationservice.model.entity.OperationEntity
import ml.ipvz.fa.operationservice.repository.OperationRepository
import ml.ipvz.fa.operationservice.service.OperationService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class OperationServiceImpl(private val operationRepository: OperationRepository) : OperationService {
    private val log by LoggerDelegate()

    override fun create(operation: Mono<OperationEventDto>): Mono<OperationEntity> = operation
        .flatMap { if (it.id != null) Mono.error { IdMustBeNullException() } else Mono.just(it) }
        .map {
            OperationEntity(
                id = null,
                groupId = it.groupId,
                description = it.description,
                detail = it.detail,
                created = Instant.now(),
                createdBy = "TODO",
                modified = Instant.now(),
                modifiedBy = "TODO"
            )
        }
        .flatMap(operationRepository::save)
        .doOnNext { log.info("Saved new ${it.detail.type} operation (${it.id}) for group (${it.groupId})") }

    override fun getAll(): Flux<OperationEntity> = operationRepository.findAll()

    override fun getAllByGroupId(groupId: Long): Flux<OperationEntity> = operationRepository.findAllByGroupId(groupId)
}
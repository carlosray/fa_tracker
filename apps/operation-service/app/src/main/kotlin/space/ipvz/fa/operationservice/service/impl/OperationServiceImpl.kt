package space.ipvz.fa.operationservice.service.impl

import org.apache.kafka.common.utils.SecurityUtils
import space.ipvz.fa.operationservice.exception.IdMustBeNullException
import space.ipvz.fa.operationservice.logging.LoggerDelegate
import space.ipvz.fa.operationservice.model.dto.OperationEventDto
import space.ipvz.fa.operationservice.model.entity.OperationEntity
import space.ipvz.fa.operationservice.repository.OperationRepository
import space.ipvz.fa.operationservice.service.OperationService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import space.ipvz.fa.async.model.event.OperationCreatedEvent
import space.ipvz.fa.async.service.send
import space.ipvz.fa.authservice.base.util.SecurityContextUtils
import java.time.Instant

@Service
class OperationServiceImpl(private val operationRepository: OperationRepository) : OperationService {
    private val log by LoggerDelegate()

    override fun create(operation: OperationEventDto): Mono<OperationEntity> = Mono.just(operation)
        .flatMap { if (it.id != null) Mono.error { IdMustBeNullException() } else Mono.just(it) }
        .zipWith(SecurityContextUtils.getUsername().defaultIfEmpty("default"))
        .map {
            OperationEntity(
                id = null,
                groupId = it.t1.groupId,
                description = it.t1.description,
                detail = it.t1.detail,
                created = Instant.now(),
                createdBy = it.t2,
                modified = Instant.now(),
                modifiedBy = it.t2
            )
        }
        .flatMap(operationRepository::save)
        .doOnNext { log.info("Saved new ${it.detail.type} operation (${it.id}) for group (${it.groupId})") }
        .publishOn(Schedulers.boundedElastic())
        .doOnNext { OperationCreatedEvent(it.groupId, it.detail).send().subscribe() }

    override fun getAll(groupId: Long?): Flux<OperationEntity> =
        if (groupId != null) operationRepository.findAllByGroupId(groupId) else operationRepository.findAll()

}
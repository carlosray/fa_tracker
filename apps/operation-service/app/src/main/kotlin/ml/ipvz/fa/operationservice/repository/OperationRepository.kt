package ml.ipvz.fa.operationservice.repository

import ml.ipvz.fa.operationservice.model.entity.OperationEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface OperationRepository : ReactiveMongoRepository<OperationEntity, String>{
    fun findAllByGroupId(groupId: Long): Flux<OperationEntity>
}
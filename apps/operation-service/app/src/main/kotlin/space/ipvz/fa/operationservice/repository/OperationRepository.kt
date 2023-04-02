package space.ipvz.fa.operationservice.repository

import space.ipvz.fa.operationservice.model.entity.OperationEntity
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface OperationRepository : ReactiveMongoRepository<OperationEntity, String> {
    fun findAllByGroupId(groupId: Long): Flux<OperationEntity>

    @Query("{ groupId: ?0, \"detail.fromAccount\": ?1 }")
    fun findAllFromAccount(groupId: Long, accountId: Long): Flux<OperationEntity>

    @Query("{ groupId: ?0, \"detail.toAccount\": ?1 }")
    fun findAllToAccount(groupId: Long, accountId: Long): Flux<OperationEntity>
}
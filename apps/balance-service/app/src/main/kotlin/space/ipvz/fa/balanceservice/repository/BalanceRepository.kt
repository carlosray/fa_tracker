package space.ipvz.fa.balanceservice.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.balanceservice.model.entity.BalanceEntity
import java.math.BigDecimal

@Repository
interface BalanceRepository : R2dbcRepository<BalanceEntity, Long> {
    fun findByGroupIdAndEntityId(groupId: Long, entityId: Long): Mono<BalanceEntity>
    fun deleteByGroupIdAndEntityId(groupId: Long, entityId: Long): Mono<Void>
    fun deleteByGroupId(groupId: Long): Mono<Void>
    fun findAllByGroupIdIn(ids: Set<Long>): Flux<BalanceEntity>

    @Query("SELECT DISTINCT group_id FROM balance")
    fun findAllDistinctGroupIds(): Flux<Long>

    @Modifying
    @Query("UPDATE balance SET amount = :amount WHERE group_id = :groupId AND entity_id = :entityId")
    fun updateAmount(groupId: Long, entityId: Long, amount: BigDecimal): Mono<Void>
}
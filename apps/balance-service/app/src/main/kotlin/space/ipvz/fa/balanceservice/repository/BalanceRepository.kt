package space.ipvz.fa.balanceservice.repository

import space.ipvz.fa.balanceservice.model.entity.BalanceEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface BalanceRepository : R2dbcRepository<BalanceEntity, Long> {
    fun findByGroupIdAndEntityId(groupId: Long, entityId: Long): Mono<BalanceEntity>
}
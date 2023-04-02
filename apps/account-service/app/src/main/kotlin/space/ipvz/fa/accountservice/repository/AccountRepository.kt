package space.ipvz.fa.accountservice.repository

import space.ipvz.fa.accountservice.model.entity.AccountEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AccountRepository : R2dbcRepository<AccountEntity, Long> {
    fun findAllByGroupId(groupId: Long): Flux<AccountEntity>
}
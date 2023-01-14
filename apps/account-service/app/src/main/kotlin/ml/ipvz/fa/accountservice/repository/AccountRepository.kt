package ml.ipvz.fa.accountservice.repository

import ml.ipvz.fa.accountservice.model.entity.AccountEntity
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : R2dbcRepository<AccountEntity, Long> {
}
package ml.ipvz.fa.accountservice.service;

import ml.ipvz.fa.accountservice.model.entity.AccountEntity
import reactor.core.publisher.Mono

interface AccountService {
    fun getAccount(groupId: Long, accountId: Long): Mono<AccountEntity>
}

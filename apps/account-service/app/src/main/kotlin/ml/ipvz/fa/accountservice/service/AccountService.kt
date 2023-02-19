package ml.ipvz.fa.accountservice.service;

import ml.ipvz.fa.accountservice.model.AccountDto
import reactor.core.publisher.Mono

interface AccountService {
    fun getAccount(groupId: Long, accountId: Long): Mono<AccountDto>
}

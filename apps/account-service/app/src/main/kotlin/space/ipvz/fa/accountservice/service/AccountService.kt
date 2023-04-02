package space.ipvz.fa.accountservice.service;

import space.ipvz.fa.accountservice.model.AccountDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AccountService {
    fun getAccount(groupId: Long, accountId: Long): Mono<AccountDto>
    fun getAccounts(groupId: Long): Flux<AccountDto>
}

package space.ipvz.fa.accountservice.service;

import space.ipvz.fa.accountservice.model.AccountDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.accountservice.model.CreateAccountDto

interface AccountService {
    fun get(groupId: Long, accountId: Long): Mono<AccountDto>
    fun get(groupId: Long): Flux<AccountDto>
    fun create(groupId: Long, account: CreateAccountDto): Mono<AccountDto>
    fun update(account: AccountDto): Mono<AccountDto>
    fun delete(accountId: Long): Mono<Void>
}

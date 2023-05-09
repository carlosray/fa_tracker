package space.ipvz.fa.accountservice.service;

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.accountservice.model.AccountDto
import space.ipvz.fa.accountservice.model.UpdateAccountDto
import space.ipvz.fa.accountservice.model.entity.AccountConfig

interface AccountService {
    fun get(groupId: Long, accountId: Long): Mono<AccountDto>
    fun getConfig(groupId: Long, accountId: Long): Mono<AccountConfig>
    fun get(groupId: Long): Flux<AccountDto>
    fun create(groupId: Long, account: UpdateAccountDto): Mono<AccountDto>
    fun update(account: UpdateAccountDto): Mono<AccountDto>
    fun delete(accountId: Long): Mono<Void>
}

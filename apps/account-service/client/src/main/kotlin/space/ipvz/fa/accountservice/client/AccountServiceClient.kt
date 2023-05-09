package space.ipvz.fa.accountservice.client

import space.ipvz.fa.accountservice.model.AccountDto
import reactor.core.publisher.Mono
import space.ipvz.fa.cloud.model.Currency

interface AccountServiceClient {
    fun getAccount(groupId: Long, id: Long): Mono<AccountDto>
    fun getAccountCurrency(groupId: Long, id: Long): Mono<Currency>
}
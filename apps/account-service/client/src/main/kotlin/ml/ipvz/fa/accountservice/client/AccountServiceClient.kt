package ml.ipvz.fa.accountservice.client

import ml.ipvz.fa.accountservice.model.AccountDto
import reactor.core.publisher.Mono

interface AccountServiceClient {
    fun getAccount(id: Long): Mono<AccountDto>
}
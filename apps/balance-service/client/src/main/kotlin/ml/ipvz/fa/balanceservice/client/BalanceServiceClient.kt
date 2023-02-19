package ml.ipvz.fa.balanceservice.client

import ml.ipvz.fa.balanceservice.model.BalanceDto
import ml.ipvz.fa.cloud.model.Currency
import reactor.core.publisher.Mono

interface BalanceServiceClient {
    fun getGroupBalance(groupId: Long, currency: Currency? = null): Mono<BalanceDto>
    fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency? = null): Mono<BalanceDto>
}
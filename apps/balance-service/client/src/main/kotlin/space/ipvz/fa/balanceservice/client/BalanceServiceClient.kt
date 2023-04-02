package space.ipvz.fa.balanceservice.client

import space.ipvz.fa.balanceservice.model.BalanceDto
import space.ipvz.fa.cloud.model.Currency
import reactor.core.publisher.Mono

interface BalanceServiceClient {
    fun getGroupBalance(groupId: Long, currency: Currency? = null): Mono<BalanceDto>
    fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency? = null): Mono<BalanceDto>
}
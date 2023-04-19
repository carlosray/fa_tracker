package space.ipvz.fa.balanceservice.service;

import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import reactor.core.publisher.Mono

interface BalanceService {
    fun getGroupBalance(groupId: Long, currency: Currency? = null): Mono<Balance>
    fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency? = null): Mono<Balance>
    fun delete(groupId: Long, entityId: Long): Mono<Void>
}

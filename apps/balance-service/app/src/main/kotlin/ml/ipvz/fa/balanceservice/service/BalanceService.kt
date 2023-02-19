package ml.ipvz.fa.balanceservice.service;

import ml.ipvz.fa.cloud.model.Balance
import ml.ipvz.fa.cloud.model.Currency
import reactor.core.publisher.Mono

interface BalanceService {
    fun getGroupBalance(groupId: Long, currency: Currency? = null): Mono<Balance>
    fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency? = null): Mono<Balance>
}

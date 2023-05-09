package space.ipvz.fa.balanceservice.service;

import reactor.core.publisher.Flux
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto
import java.math.BigDecimal
import java.time.Instant

interface BalanceService {
    fun findAllGroupIds(): Flux<Long>
    fun getGroupBalance(groupId: Long, currency: Currency? = null): Mono<Balance>
    fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency? = null): Mono<Balance>
    fun delete(groupId: Long, entityId: Long): Mono<Void>
    fun deleteAll(groupId: Long): Mono<Void>
    fun add(groupId: Long, entityId: Long, amount: BigDecimal, currency: Currency): Mono<Void>
    fun getAccountCurrency(groupId: Long, id: Long): Mono<Currency>
    fun applyAmounts(accountAmounts: Collection<UpdateAmountsDto.Amount>): Mono<Void>
    fun recalculateGroupBalance(ids: Set<Long>): Mono<Void>
}

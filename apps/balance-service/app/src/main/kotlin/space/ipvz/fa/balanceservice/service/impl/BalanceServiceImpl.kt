package space.ipvz.fa.balanceservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import space.ipvz.fa.accountservice.client.AccountServiceClient
import space.ipvz.fa.balanceservice.logging.LoggerDelegate
import space.ipvz.fa.balanceservice.model.entity.BalanceEntity
import space.ipvz.fa.balanceservice.repository.BalanceRepository
import space.ipvz.fa.balanceservice.service.BalanceService
import space.ipvz.fa.balanceservice.service.CurrencyService
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import space.ipvz.fa.groupservice.client.GroupServiceClient
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto
import java.math.BigDecimal
import java.time.Duration

@Service
class BalanceServiceImpl(
    private val balanceRepository: BalanceRepository,
    private val currencyService: CurrencyService,
    private val accountServiceClient: AccountServiceClient,
    private val groupServiceClient: GroupServiceClient,
) : BalanceService {
    private val log by LoggerDelegate()

    override fun findAllGroupIds(): Flux<Long> = balanceRepository.findAllDistinctGroupIds()

    override fun getGroupBalance(groupId: Long, currency: Currency?): Mono<Balance> =
        getBalance(groupId, groupId, currency)

    override fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency?): Mono<Balance> =
        getBalance(groupId, accountId, currency)

    override fun delete(groupId: Long, entityId: Long): Mono<Void> =
        balanceRepository.deleteByGroupIdAndEntityId(groupId, entityId)

    override fun deleteAll(groupId: Long): Mono<Void> = balanceRepository.deleteByGroupId(groupId)

    private fun getBalance(groupId: Long, entityId: Long, currency: Currency?): Mono<Balance> =
        balanceRepository.findByGroupIdAndEntityId(groupId, entityId)
            .flatMap {
                val balance = Balance(it.amount, it.currency, it.modified)
                val isCurrencyDifferent = currency != null && balance.currency != currency

                if (isCurrencyDifferent) currencyService.applyCurrency(balance, currency!!) else Mono.just(balance)
            }

    override fun add(groupId: Long, entityId: Long, amount: BigDecimal, currency: Currency): Mono<Void> =
        findOrCreate(groupId, entityId, BigDecimal.ZERO, currency)
            .flatMap { balanceRepository.save(it.copy(amount = it.amount + amount)) }
            .then()

    private fun findOrCreate(
        groupId: Long,
        entityId: Long,
        amount: BigDecimal,
        currency: Currency
    ): Mono<BalanceEntity> = balanceRepository.findByGroupIdAndEntityId(groupId, entityId)
        .switchIfEmpty {
            Mono.just(
                BalanceEntity(
                    groupId = groupId,
                    entityId = entityId,
                    currency = currency,
                    amount = amount
                )
            )
        }
        .flatMap { balanceRepository.save(it.copy(currency = currency)) }

    override fun getAccountCurrency(groupId: Long, id: Long): Mono<Currency> = this.getAccountBalance(groupId, id)
        .map { it.currency }
        .switchIfEmpty { accountServiceClient.getAccountCurrency(groupId, id) }
        .cache(Duration.ofHours(1))

    override fun applyAmounts(accountAmounts: Collection<UpdateAmountsDto.Amount>): Mono<Void> =
        balanceRepository.saveAll(Flux.fromIterable(accountAmounts)
            .flatMap { a ->
                balanceRepository.findByGroupIdAndEntityId(a.groupId, a.id)
                    .map { it.copy(amount = a.amount) }
                    .switchIfEmpty {
                        getAccountCurrency(a.groupId, a.id).map { c ->
                            BalanceEntity(groupId = a.groupId, entityId = a.id, currency = c, amount = a.amount)
                        }
                    }
            })
            .then(
                recalculateGroupBalance(accountAmounts.mapTo(HashSet()) { it.groupId })
            )

    override fun recalculateGroupBalance(ids: Set<Long>): Mono<Void> {
        return groupServiceClient.getGroupsPrivate(ids)
            .flatMap { findOrCreate(it.id, it.id, it.balance.amount, it.balance.currency) }
            .then(balanceRepository.findAllByGroupIdIn(ids)
                .collectList()
                .flatMapMany { all ->
                    val groupEntities = all.filter { it.groupId == it.entityId && it.groupId in ids }.associateBy { it.groupId }
                    Flux.concat(all
                        .filter { it.groupId in groupEntities && it.groupId != it.entityId }
                        .groupBy { it.groupId }
                        .mapValues { (gId, entities) ->
                            val groupCurrency = groupEntities[gId]!!.currency

                            Flux.fromStream(entities.stream())
                                .flatMap {
                                    if (groupCurrency == it.currency) Mono.just(it.amount)
                                    else currencyService.applyCurrency(it.amount, it.currency, groupCurrency)
                                }
                                .reduce(BigDecimal.ZERO) { t, u -> t + u }
                        }
                        .map { (gId, amount) ->
                            amount.flatMap { balanceRepository.updateAmount(gId, gId, it) }
                        })
                }
                .then()
            )
    }
}
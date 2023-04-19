package space.ipvz.fa.balanceservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import space.ipvz.fa.balanceservice.logging.LoggerDelegate
import space.ipvz.fa.balanceservice.model.entity.BalanceEntity
import space.ipvz.fa.balanceservice.repository.BalanceRepository
import space.ipvz.fa.balanceservice.service.BalanceService
import space.ipvz.fa.balanceservice.service.CurrencyService
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import java.math.BigDecimal

@Service
class BalanceServiceImpl(
    private val balanceRepository: BalanceRepository,
    private val currencyService: CurrencyService
) : BalanceService {
    private val log by LoggerDelegate()

    override fun getGroupBalance(groupId: Long, currency: Currency?): Mono<Balance> =
        getOrCreateBalance(groupId, groupId, currency)

    override fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency?): Mono<Balance> =
        getOrCreateBalance(groupId, accountId, currency)

    override fun delete(groupId: Long, entityId: Long): Mono<Void> =
        balanceRepository.deleteByGroupIdAndEntityId(groupId, entityId)
            .doOnNext { log.info("Deleted balance with groupId: $groupId and entityId: $entityId") }

    private fun getOrCreateBalance(groupId: Long, entityId: Long, currency: Currency?): Mono<Balance> =
        balanceRepository.findByGroupIdAndEntityId(groupId, entityId)
            .map {
                val balance = Balance(it.amount, it.currency, it.modified)
                val isCurrencyDifferent = currency != null && balance.currency != currency

                if (isCurrencyDifferent) currencyService.applyCurrency(balance, currency!!) else balance
            }

    private fun createEmptyBalance(groupId: Long, entityId: Long, currency: Currency?): Mono<BalanceEntity> =
        balanceRepository.save(
            BalanceEntity(
                groupId = groupId,
                entityId = entityId,
                currency = currency ?: Currency.USD,
                amount = BigDecimal.ZERO
            )
        )
}
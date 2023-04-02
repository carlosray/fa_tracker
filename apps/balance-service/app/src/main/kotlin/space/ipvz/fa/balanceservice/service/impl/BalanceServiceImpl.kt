package space.ipvz.fa.balanceservice.service.impl

import space.ipvz.fa.balanceservice.repository.BalanceRepository
import space.ipvz.fa.balanceservice.service.BalanceService
import space.ipvz.fa.balanceservice.service.CurrencyService
import space.ipvz.fa.cloud.model.Balance
import space.ipvz.fa.cloud.model.Currency
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BalanceServiceImpl(
    private val balanceRepository: BalanceRepository,
    private val currencyService: CurrencyService
) : BalanceService {
    override fun getGroupBalance(groupId: Long, currency: Currency?): Mono<Balance> =
        getBalance(groupId, groupId, currency)

    override fun getAccountBalance(groupId: Long, accountId: Long, currency: Currency?): Mono<Balance> =
        getBalance(groupId, accountId, currency)

    private fun getBalance(groupId: Long, entityId: Long, currency: Currency?): Mono<Balance> {
        return balanceRepository.findByGroupIdAndEntityId(groupId, entityId)
            .map {
                val balance = Balance(it.amount, it.currency, it.modified)
                val isCurrencyDifferent = currency != null && balance.currency != currency

                if (isCurrencyDifferent) currencyService.applyCurrency(balance, currency!!) else balance
            }
    }
}
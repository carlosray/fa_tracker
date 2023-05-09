package space.ipvz.fa.operationservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import space.ipvz.fa.operationservice.model.dto.Expense
import space.ipvz.fa.operationservice.model.dto.Income
import space.ipvz.fa.operationservice.model.dto.Transfer
import space.ipvz.fa.operationservice.model.dto.UpdateAmountsDto
import space.ipvz.fa.operationservice.service.BalanceService
import space.ipvz.fa.operationservice.service.OperationService
import java.math.BigDecimal
import java.time.Instant

@Service
class BalanceServiceImpl(
    private val operationService: OperationService
) : BalanceService {
    override fun getBalances(): Mono<UpdateAmountsDto> =
        operationService.getAll()
            .collectList()
            .map { ops ->
                val accounts: MutableMap<Long, UpdateAmountsDto.Amount> = mutableMapOf()
                ops.forEach { oe ->
                    val o = oe.detail
                    when (o) {
                        is Income -> accounts.add(o.toAccount, oe.groupId, o.amount, oe.created)
                        is Expense -> accounts.add(o.fromAccount, oe.groupId, o.amount.negate(), oe.created)
                        is Transfer -> {
                            accounts.add(o.fromAccount, oe.groupId, o.amount.negate(), oe.created)
                            accounts.add(o.toAccount, oe.groupId, o.resultAmount, oe.created)
                        }
                    }
                }
                UpdateAmountsDto(accounts.values)
            }

    private fun MutableMap<Long, UpdateAmountsDto.Amount>.add(
        id: Long,
        groupId: Long,
        amount: BigDecimal,
        update: Instant
    ) {
        this.compute(id) { _, a ->
            a?.copy(
                amount = a.amount + amount,
                lastUpdate = if (update > a.lastUpdate) update else a.lastUpdate
            ) ?: UpdateAmountsDto.Amount(id, groupId, amount, update)
        }
    }
}
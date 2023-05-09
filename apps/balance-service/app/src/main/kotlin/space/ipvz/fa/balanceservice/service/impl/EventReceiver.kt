package space.ipvz.fa.balanceservice.service.impl

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.model.event.GroupCurrencyChangedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.model.event.OperationCreatedEvent
import space.ipvz.fa.async.service.EventService
import space.ipvz.fa.balanceservice.service.BalanceService
import space.ipvz.fa.cloud.model.Currency
import space.ipvz.fa.operationservice.model.dto.Expense
import space.ipvz.fa.operationservice.model.dto.Income
import space.ipvz.fa.operationservice.model.dto.Transfer

@Service
class EventReceiver(
    private val eventService: EventService,
    private val balanceService: BalanceService,
) {
    @PostConstruct
    fun postConstruct() {
        eventService.group.deleted.receive().subscribe(::onGroupDeleted)
        eventService.group.currencyChanged.receive().subscribe(::onGroupCurrencyChanged)
        eventService.account.deleted.receive().subscribe(::onAccountDeleted)
        eventService.operation.created.receive().subscribe(::onOperationCreated)
    }

    private fun onGroupDeleted(event: GroupDeletedEvent) {
        balanceService.deleteAll(event.groupId).subscribe()
    }

    private fun onGroupCurrencyChanged(event: GroupCurrencyChangedEvent) {
        balanceService.recalculateGroupBalance(setOf(event.groupId)).subscribe()
    }

    private fun onAccountDeleted(event: AccountDeletedEvent) {
        balanceService.delete(event.groupId, event.accountId).subscribe()
    }

    private fun onOperationCreated(event: OperationCreatedEvent) {
        val op = event.operation
        val groupId = event.groupId
        when (op) {
            is Income -> getAccountCurrency(groupId, op.toAccount).flatMap {
                balanceService.add(
                    event.groupId,
                    op.toAccount,
                    op.amount,
                    it
                )
            }

            is Expense -> getAccountCurrency(groupId, op.fromAccount).flatMap {
                balanceService.add(
                    event.groupId,
                    op.fromAccount,
                    op.amount.negate(),
                    it
                )
            }

            is Transfer -> Mono.zip(
                getAccountCurrency(groupId, op.fromAccount),
                getAccountCurrency(groupId, op.toAccount)
            )
                .flatMap {
                    Mono.`when`(
                        balanceService.add(event.groupId, op.fromAccount, op.amount.negate(), it.t1),
                        balanceService.add(event.groupId, op.toAccount, op.resultAmount, it.t2)
                    )
                }
        }.then(balanceService.recalculateGroupBalance(setOf(groupId))).subscribe()
    }

    private fun getAccountCurrency(groupId: Long, id: Long): Mono<Currency> =
        balanceService.getAccountCurrency(groupId, id)
}
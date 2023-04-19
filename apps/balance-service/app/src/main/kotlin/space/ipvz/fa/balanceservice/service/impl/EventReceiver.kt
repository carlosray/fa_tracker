package space.ipvz.fa.balanceservice.service.impl

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.service.EventService
import space.ipvz.fa.balanceservice.service.BalanceService

@Service
class EventReceiver(
    private val eventService: EventService,
    private val balanceService: BalanceService
) {
    @PostConstruct
    fun postConstruct() {
        eventService.group.deleted.receive().subscribe(::onGroupDeleted)
        eventService.account.deleted.receive().subscribe(::onAccountDeleted)
    }

    private fun onGroupDeleted(event: GroupDeletedEvent) {
        balanceService.delete(event.groupId, event.groupId).subscribe()
    }

    private fun onAccountDeleted(event: AccountDeletedEvent) {
        balanceService.delete(event.groupId, event.accountId).subscribe()
    }
}
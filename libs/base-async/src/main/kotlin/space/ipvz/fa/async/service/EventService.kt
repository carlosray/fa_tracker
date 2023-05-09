package space.ipvz.fa.async.service

import reactor.core.publisher.Mono
import space.ipvz.fa.async.model.Event
import space.ipvz.fa.async.model.event.AccountCreatedEvent
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.model.event.GroupCreatedEvent
import space.ipvz.fa.async.model.event.GroupCurrencyChangedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.model.event.OperationCreatedEvent
import space.ipvz.fa.async.model.event.PermissionUpdatedEvent
import space.ipvz.fa.async.util.ContextUtils

interface EventService {
    val group: Group
    val user: User
    val account: Account
    val category: Category
    val operation: Operation

    interface Group {
        val deleted: Event<GroupDeletedEvent>
        val created: Event<GroupCreatedEvent>
        val currencyChanged: Event<GroupCurrencyChangedEvent>
    }

    interface User {
        val permissionUpdated: Event<PermissionUpdatedEvent>
    }

    interface Account {
        val created: Event<AccountCreatedEvent>
        val deleted: Event<AccountDeletedEvent>
    }

    interface Category

    interface Operation {
        val created: Event<OperationCreatedEvent>
    }
}

private val eventService: Lazy<EventService> = lazy { ContextUtils.getBean(EventService::class.java) }

fun GroupDeletedEvent.send(): Mono<Void> = eventService.value.group.deleted.send(this)
fun GroupCreatedEvent.send(): Mono<Void> = eventService.value.group.created.send(this)
fun GroupCurrencyChangedEvent.send(): Mono<Void> = eventService.value.group.currencyChanged.send(this)
fun PermissionUpdatedEvent.send(): Mono<Void> = eventService.value.user.permissionUpdated.send(this)
fun AccountCreatedEvent.send(): Mono<Void> = eventService.value.account.created.send(this)
fun AccountDeletedEvent.send(): Mono<Void> = eventService.value.account.deleted.send(this)
fun OperationCreatedEvent.send(): Mono<Void> = eventService.value.operation.created.send(this)
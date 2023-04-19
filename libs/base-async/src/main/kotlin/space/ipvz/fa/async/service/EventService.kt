package space.ipvz.fa.async.service

import reactor.core.publisher.Mono
import space.ipvz.fa.async.model.Event
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.model.event.GroupCreatedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.model.event.PermissionUpdatedEvent
import space.ipvz.fa.async.util.ContextUtils

interface EventService {
    val group: Group
    val user: User
    val account: Account
    val category: Category

    interface Group {
        val deleted: Event<GroupDeletedEvent>
        val created: Event<GroupCreatedEvent>
    }

    interface User {
        val permissionUpdated: Event<PermissionUpdatedEvent>
    }

    interface Account {
        val deleted: Event<AccountDeletedEvent>
    }

    interface Category
}

private val eventService: Lazy<EventService> = lazy { ContextUtils.getBean(EventService::class.java) }

fun GroupDeletedEvent.send(): Mono<Void> = eventService.value.group.deleted.send(this)
fun GroupCreatedEvent.send(): Mono<Void> = eventService.value.group.created.send(this)
fun PermissionUpdatedEvent.send(): Mono<Void> = eventService.value.user.permissionUpdated.send(this)
fun AccountDeletedEvent.send(): Mono<Void> = eventService.value.account.deleted.send(this)
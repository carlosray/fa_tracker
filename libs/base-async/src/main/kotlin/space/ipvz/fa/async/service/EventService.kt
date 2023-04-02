package space.ipvz.fa.async.service

import space.ipvz.fa.async.model.Event
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.model.event.PermissionUpdatedEvent
import space.ipvz.fa.async.util.ContextUtils
import reactor.core.publisher.Mono

interface EventService {
    val group: Group
    val user: User
    val account: Account
    val category: Category

    interface Group {
        val deleted: Event<GroupDeletedEvent>
    }

    interface User {
        val permissionUpdated: Event<PermissionUpdatedEvent>
    }

    interface Account
    interface Category
}

private val eventService: Lazy<EventService> = lazy { ContextUtils.getBean(EventService::class.java) }

fun GroupDeletedEvent.send(): Mono<Void> = eventService.value.group.deleted.send(this)
fun PermissionUpdatedEvent.send(): Mono<Void> = eventService.value.user.permissionUpdated.send(this)
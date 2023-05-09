package space.ipvz.fa.async.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import space.ipvz.fa.async.model.Event
import space.ipvz.fa.async.model.EventEntity
import space.ipvz.fa.async.model.event.AccountCreatedEvent
import space.ipvz.fa.async.model.event.AccountDeletedEvent
import space.ipvz.fa.async.model.event.GroupCreatedEvent
import space.ipvz.fa.async.model.event.GroupCurrencyChangedEvent
import space.ipvz.fa.async.model.event.GroupDeletedEvent
import space.ipvz.fa.async.model.event.OperationCreatedEvent
import space.ipvz.fa.async.model.event.PermissionUpdatedEvent

abstract class AbstractEventService : EventService {
    override val group = object : EventService.Group {
        override val deleted = buildEvent<GroupDeletedEvent>()
        override val created = buildEvent<GroupCreatedEvent>()
        override val currencyChanged = buildEvent<GroupCurrencyChangedEvent>()
    }
    override val user = object : EventService.User {
        override val permissionUpdated = buildEvent<PermissionUpdatedEvent>()
    }
    override val account = object : EventService.Account {
        override val created = buildEvent<AccountCreatedEvent>()
        override val deleted = buildEvent<AccountDeletedEvent>()
    }
    override val category = object : EventService.Category {}

    override val operation: EventService.Operation = object : EventService.Operation {
        override val created = buildEvent<OperationCreatedEvent>()
    }

    private inline fun <reified E : EventEntity> buildEvent(inputTopic: String? = null): Event<E> {
        val className = E::class.simpleName
        if (inputTopic.isNullOrBlank() && className.isNullOrBlank()) {
            throw IllegalStateException("Specify topic")
        }
        val topic = if (inputTopic.isNullOrBlank()) className!!.toTopicByCamelCase() else inputTopic

        return object : Event<E> {
            override fun send(event: E): Mono<Void> = this@AbstractEventService.send(event, topic)
            override fun receive(): Flux<E> = this@AbstractEventService.receive(topic)
        }
    }

    private fun String.toTopicByCamelCase(): String = this.replace("([a-z])([A-Z]+)".toRegex(), "$1-$2").lowercase()

    protected abstract fun <E : EventEntity> send(event: E, topic: String): Mono<Void>
    protected abstract fun <E : EventEntity> receive(topic: String): Flux<E>
}
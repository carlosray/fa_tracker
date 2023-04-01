package ml.ipvz.fa.cloud.async.service

import ml.ipvz.fa.cloud.async.model.Event
import ml.ipvz.fa.cloud.async.model.EventEntity
import ml.ipvz.fa.cloud.async.model.event.GroupCreatedEvent
import ml.ipvz.fa.cloud.async.model.event.PermissionUpdatedEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class AbstractEventService : EventService {
    override val group = object : EventService.Group {
        override val created = buildEvent<GroupCreatedEvent>()
    }
    override val user = object : EventService.User {
        override val permissionUpdated = buildEvent<PermissionUpdatedEvent>()
    }
    override val account = object : EventService.Account {}
    override val category = object : EventService.Category {}


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
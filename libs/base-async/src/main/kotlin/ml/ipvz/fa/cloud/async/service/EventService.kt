package ml.ipvz.fa.cloud.async.service

import ml.ipvz.fa.cloud.async.model.Event
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EventService<E : Event> {
    fun send(event: E): Mono<Void>
    fun receive(): Flux<E>
}
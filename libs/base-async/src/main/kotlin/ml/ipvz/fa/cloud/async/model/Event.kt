package ml.ipvz.fa.cloud.async.model

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface Event<E : EventEntity> {
    fun send(event: E): Mono<Void>
    fun receive(): Flux<E>
}
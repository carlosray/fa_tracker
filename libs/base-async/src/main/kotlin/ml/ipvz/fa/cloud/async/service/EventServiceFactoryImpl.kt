package ml.ipvz.fa.cloud.async.service

import ml.ipvz.fa.cloud.async.model.Event
import ml.ipvz.fa.cloud.async.model.event.group.GroupCreatedEvent
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.receiver.ReceiverRecord
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import reactor.kafka.sender.SenderResult

class EventServiceFactoryImpl(
    private val kafkaSender: KafkaSender<String, Event>,
    private val receiverOptions: ReceiverOptions<String, Event>
) : EventServiceFactory {

    companion object {
        private val log = LoggerFactory.getLogger(EventServiceFactoryImpl::class.java)
    }

    override val group = object : EventServiceFactory.Group {
        override val created = eventService<GroupCreatedEvent>(GroupCreatedEvent.TOPIC)
    }
    override val user = object : EventServiceFactory.User {}
    override val account = object : EventServiceFactory.Account {}
    override val category = object : EventServiceFactory.Category {}

    private fun <E : Event> eventService(topic: String): EventService<E> = object : EventService<E> {
        override fun send(event: E): Mono<Void> = this@EventServiceFactoryImpl.send(event, topic)
        override fun receive(): Flux<E> = this@EventServiceFactoryImpl.receive(topic)
    }

    private fun <E : Event> send(event: E, topic: String): Mono<Void> {
        val record = ProducerRecord<String, Event>(topic, event.eventKey, event)

        return kafkaSender.send(Mono.just(SenderRecord.create(record, record.key())))
            .doOnNext { result: SenderResult<String?> ->
                log.debug(
                    "Send event {} to topic {} partition {}",
                    result.correlationMetadata(),
                    result.recordMetadata().topic(),
                    result.recordMetadata().partition()
                )
            }
            .next()
            .doOnError { e: Throwable? -> log.error("Sending event error", e) }
            .then()
    }

    private fun <E : Event> receive(topic: String): Flux<E> =
        KafkaReceiver.create(receiverOptions.subscription(setOf(topic))).receive()
            .doOnNext { r: ReceiverRecord<String, Event> ->
                log.debug(
                    "Received event {} from topic {} partition {}",
                    r.key(),
                    r.topic(),
                    r.partition()
                )
            }
            .doOnNext { r: ReceiverRecord<String, Event> -> r.receiverOffset().acknowledge() }
            .doOnError { e: Throwable? -> log.error("Receiving event error", e) }
            .map { r: ReceiverRecord<String, Event> -> r.value() as E }
}
package space.ipvz.fa.async.service

import space.ipvz.fa.async.model.EventEntity
import org.apache.kafka.clients.producer.ProducerRecord
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.receiver.ReceiverRecord
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import reactor.kafka.sender.SenderResult
import space.ipvz.fa.async.logging.LoggerDelegate

class EventServiceImpl(
    private val kafkaSender: KafkaSender<String, EventEntity>,
    private val receiverOptions: ReceiverOptions<String, EventEntity>
) : AbstractEventService() {
    private val log by LoggerDelegate()

    override fun <E : EventEntity> send(event: E, topic: String): Mono<Void> {
        val record = ProducerRecord<String, EventEntity>(topic, event.eventKey, event)

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

    override fun <E : EventEntity> receive(topic: String): Flux<E> =
        KafkaReceiver.create(receiverOptions.subscription(setOf(topic))).receive()
            .doOnNext { r: ReceiverRecord<String, EventEntity> ->
                log.debug(
                    "Received event {} from topic {} partition {}",
                    r.key(),
                    r.topic(),
                    r.partition()
                )
            }
            .doOnNext { r: ReceiverRecord<String, EventEntity> -> r.receiverOffset().acknowledge() }
            .doOnError { e: Throwable? -> log.error("Receiving event error", e) }
            .map { r: ReceiverRecord<String, EventEntity> -> r.value() as E }
}
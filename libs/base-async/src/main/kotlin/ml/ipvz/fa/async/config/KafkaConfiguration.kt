package ml.ipvz.fa.async.config

import ml.ipvz.fa.async.model.EventEntity
import ml.ipvz.fa.async.service.EventService
import ml.ipvz.fa.async.service.EventServiceImpl
import ml.ipvz.fa.async.util.ContextUtils
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions

@Configuration
@EnableKafka
class KafkaConfiguration {
    @Value("\${spring.kafka.server}")
    private lateinit var bootstrapServers: String

    @Value("\${spring.application.name}")
    private lateinit var groupId: String

    @Bean
    fun receiverOptions(): ReceiverOptions<String, EventEntity> = ReceiverOptions.create(consumerProps())

    private fun consumerProps(): Map<String, Any> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ConsumerConfig.GROUP_ID_CONFIG to groupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        JsonDeserializer.TRUSTED_PACKAGES to "ml.ipvz.fa.*"
    )

    @Bean(destroyMethod = "close")
    fun kafkaSender(): KafkaSender<String, EventEntity> {
        val senderOptions = SenderOptions.create<String, EventEntity>(senderProps())
            .maxInFlight(1024)
        return KafkaSender.create(senderOptions)
    }

    private fun senderProps(): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ProducerConfig.LINGER_MS_CONFIG to 10,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
    )

    @Bean
    fun eventService(
        receiverOptions: ReceiverOptions<String, EventEntity>,
        sender: KafkaSender<String, EventEntity>
    ): EventService = EventServiceImpl(sender, receiverOptions)

    @Bean
    fun contextUtils() = ContextUtils()
}
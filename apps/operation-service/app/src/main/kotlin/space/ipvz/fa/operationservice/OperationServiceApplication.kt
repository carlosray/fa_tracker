package space.ipvz.fa.operationservice

import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import space.ipvz.fa.exchange.logging.LoggingWebFilter
import space.ipvz.fa.operationservice.config.OperationClientConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import space.ipvz.fa.async.config.KafkaConfiguration

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [OperationClientConfiguration::class])
    ]
)
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class, KafkaConfiguration::class)
class OperationServiceApplication

fun main(args: Array<String>) {
    runApplication<OperationServiceApplication>(*args)
}

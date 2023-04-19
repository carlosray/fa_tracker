package space.ipvz.fa.balanceservice

import space.ipvz.fa.balanceservice.config.BalanceClientConfiguration
import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import space.ipvz.fa.exchange.logging.LoggingWebFilter
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
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [BalanceClientConfiguration::class])
    ]
)
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class, KafkaConfiguration::class)
class BalanceServiceApplication

fun main(args: Array<String>) {
    runApplication<BalanceServiceApplication>(*args)
}

package ml.ipvz.fa.balanceservice

import ml.ipvz.fa.balanceservice.config.BalanceClientConfiguration
import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import ml.ipvz.fa.exchange.logging.LoggingWebFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [BalanceClientConfiguration::class])
    ]
)
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class)
class BalanceServiceApplication

fun main(args: Array<String>) {
    runApplication<BalanceServiceApplication>(*args)
}

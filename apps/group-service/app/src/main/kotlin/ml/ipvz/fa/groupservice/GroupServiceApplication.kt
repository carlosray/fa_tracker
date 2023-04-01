package ml.ipvz.fa.groupservice

import ml.ipvz.fa.balanceservice.config.BalanceClientConfiguration
import ml.ipvz.fa.async.config.KafkaConfiguration
import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import ml.ipvz.fa.exchange.logging.LoggingWebFilter
import ml.ipvz.fa.groupservice.config.GroupClientConfiguration
import ml.ipvz.fa.userservice.config.UserClientConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [GroupClientConfiguration::class])
    ]
)
@Import(
    LoggingWebFilter::class,
    BaseApiExceptionHandler::class,
    KafkaConfiguration::class,
    BalanceClientConfiguration::class,
    UserClientConfiguration::class
)
class GroupServiceApplication

fun main(args: Array<String>) {
    runApplication<GroupServiceApplication>(*args)
}

package space.ipvz.fa.groupservice

import space.ipvz.fa.balanceservice.config.BalanceClientConfiguration
import space.ipvz.fa.async.config.KafkaConfiguration
import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import space.ipvz.fa.exchange.logging.LoggingWebFilter
import space.ipvz.fa.groupservice.config.GroupClientConfiguration
import space.ipvz.fa.userservice.config.UserClientConfiguration
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

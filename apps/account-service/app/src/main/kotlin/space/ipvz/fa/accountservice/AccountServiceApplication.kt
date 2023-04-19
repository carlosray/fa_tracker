package space.ipvz.fa.accountservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import space.ipvz.fa.accountservice.config.AccountClientConfiguration
import space.ipvz.fa.async.config.KafkaConfiguration
import space.ipvz.fa.balanceservice.config.BalanceClientConfiguration
import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import space.ipvz.fa.exchange.logging.LoggingWebFilter

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [AccountClientConfiguration::class])
    ]
)
@Import(
    LoggingWebFilter::class,
    BaseApiExceptionHandler::class,
    BalanceClientConfiguration::class,
    KafkaConfiguration::class
)
class AccountServiceApplication

fun main(args: Array<String>) {
    runApplication<AccountServiceApplication>(*args)
}

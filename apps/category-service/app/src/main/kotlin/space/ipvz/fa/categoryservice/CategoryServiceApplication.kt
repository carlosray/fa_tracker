package space.ipvz.fa.categoryservice

import space.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import space.ipvz.fa.exchange.logging.LoggingWebFilter
import space.ipvz.fa.categoryservice.config.CategoryClientConfiguration
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
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [CategoryClientConfiguration::class])
    ]
)
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class, KafkaConfiguration::class)
class CategoryServiceApplication

fun main(args: Array<String>) {
    runApplication<CategoryServiceApplication>(*args)
}

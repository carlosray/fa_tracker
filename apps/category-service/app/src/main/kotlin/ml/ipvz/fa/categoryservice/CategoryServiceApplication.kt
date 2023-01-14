package ml.ipvz.fa.categoryservice

import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import ml.ipvz.fa.exchange.logging.LoggingWebFilter
import ml.ipvz.fa.categoryservice.config.CategoryClientConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [CategoryClientConfiguration::class])
    ]
)
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class)
class CategoryServiceApplication

fun main(args: Array<String>) {
    runApplication<CategoryServiceApplication>(*args)
}

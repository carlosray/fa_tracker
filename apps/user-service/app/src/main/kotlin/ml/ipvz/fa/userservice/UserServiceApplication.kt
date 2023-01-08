package ml.ipvz.fa.userservice

import ml.ipvz.fa.error.handling.handler.BaseApiExceptionHandler
import ml.ipvz.fa.exchange.logging.LoggingWebFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(LoggingWebFilter::class, BaseApiExceptionHandler::class)
class UserServiceApplication

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}

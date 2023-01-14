package ml.ipvz.fa.categoryservice.exception.handler

import ml.ipvz.fa.categoryservice.logging.LoggerDelegate
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.handler.ResponseStatusExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseStatusExceptionHandler() {
    private val log by LoggerDelegate()
}
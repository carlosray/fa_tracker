package ml.ipvz.fa.operationservice.exception.handler

import ml.ipvz.fa.operationservice.logging.LoggerDelegate
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.handler.ResponseStatusExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseStatusExceptionHandler() {
    private val log by LoggerDelegate()
}
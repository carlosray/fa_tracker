package space.ipvz.fa.userservice.exception.handler

import space.ipvz.fa.userservice.logging.LoggerDelegate
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.handler.ResponseStatusExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseStatusExceptionHandler() {
    private val log by LoggerDelegate()
}
package space.ipvz.fa.error.handling.handler;

import java.time.Instant;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import space.ipvz.fa.error.handling.dto.ErrorDto;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
@Order
public class BaseApiExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ErrorDto>> responseStatusException(ResponseStatusException e) {
        log.error("Response status exception", e);
        HttpStatusCode httpStatusCode = e.getStatusCode();
        ErrorDto error = new ErrorDto(httpStatusCode.value(), e.getMessage(), Instant.now());
        return Mono.just(new ResponseEntity<>(error, httpStatusCode));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDto>> unexpected(Exception e) {
        log.error("Unexpected exception", e);
        HttpStatus status = Optional.ofNullable(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class))
                .map(ResponseStatus::code)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorDto errorDto = new ErrorDto(status.value(), e.getMessage(), Instant.now());
        return Mono.just(ResponseEntity.status(status).body(errorDto));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<ResponseEntity<ErrorDto>> handleClientError(WebClientResponseException ex) {
        String path = Optional.ofNullable(ex.getRequest()).map(r -> r.getURI().getPath()).orElse("");
        log.error("Error from client call " + path, ex);
        ErrorDto errorDto;
        try {
            errorDto = ex.getResponseBodyAs(ErrorDto.class);
        } catch (Exception e) {
            log.debug("Error while parsing errorDto from client call", e);
            errorDto = new ErrorDto(ex.getStatusCode().value(), ex.getMessage(), Instant.now());
        }
        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorDto));
    }

}

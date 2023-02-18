package ml.ipvz.fa.authservice.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessForbiddenException extends ResponseStatusException {
    private final String message;

    public AccessForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

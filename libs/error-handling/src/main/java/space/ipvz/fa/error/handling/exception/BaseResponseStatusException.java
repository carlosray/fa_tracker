package space.ipvz.fa.error.handling.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseResponseStatusException extends ResponseStatusException {
    private final String message;

    public BaseResponseStatusException(HttpStatusCode status, String message) {
        super(status, message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

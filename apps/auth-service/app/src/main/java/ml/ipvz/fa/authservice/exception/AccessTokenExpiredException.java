package ml.ipvz.fa.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessTokenExpiredException extends ResponseStatusException {

    public AccessTokenExpiredException(Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, "Access token expired", cause);
    }
}

package ml.ipvz.fa.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenInvalidException extends ResponseStatusException {
    public RefreshTokenInvalidException(String token) {
        super(HttpStatus.BAD_REQUEST, "Refresh token invalid. Provided token: " + token);
    }
}

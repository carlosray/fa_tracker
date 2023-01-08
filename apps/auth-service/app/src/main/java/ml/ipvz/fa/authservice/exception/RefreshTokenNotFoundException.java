package ml.ipvz.fa.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenNotFoundException extends ResponseStatusException {
    public RefreshTokenNotFoundException(String clientId) {
        super(HttpStatus.BAD_REQUEST, "Refresh token not found for client " + clientId);
    }
}

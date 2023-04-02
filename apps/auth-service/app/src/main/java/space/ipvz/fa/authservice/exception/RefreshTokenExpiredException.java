package space.ipvz.fa.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenExpiredException extends ResponseStatusException {
    public RefreshTokenExpiredException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh token expired");
    }
}

package space.ipvz.fa.authservice.base.exception;

import space.ipvz.fa.authservice.base.permission.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessForbiddenException extends ResponseStatusException {

    public AccessForbiddenException(Permission permission) {
        super(HttpStatus.FORBIDDEN, "Access denied. Required role %s for resource %s in group %s"
                .formatted(permission.role, permission.resource, permission.groupId));
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}

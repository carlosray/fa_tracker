package ml.ipvz.fa.authservice.base.service;

import ml.ipvz.fa.authservice.base.permission.Permission;
import reactor.core.publisher.Mono;

public interface PermissionChecker {
    /**
     * Check current user has {@param permission}
     */
    Mono<Boolean> check(Permission permission);
}

package ml.ipvz.fa.authservice.base.service;

import ml.ipvz.fa.authservice.base.permission.Permission;
import reactor.core.publisher.Mono;

public interface PermissionChecker {
    Mono<Boolean> check(Permission permission);
}

package ml.ipvz.fa.authservice.service;

import java.util.List;

import ml.ipvz.fa.authservice.base.permission.Permission;
import reactor.core.publisher.Mono;

public interface CacheService {
    Mono<List<Permission>> getUserPermissions(Long id);
}

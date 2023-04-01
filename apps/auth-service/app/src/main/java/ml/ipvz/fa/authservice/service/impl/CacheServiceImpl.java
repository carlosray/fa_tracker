package ml.ipvz.fa.authservice.service.impl;

import java.util.List;

import jakarta.annotation.PostConstruct;
import ml.ipvz.fa.async.service.EventService;
import ml.ipvz.fa.authservice.base.permission.Permission;
import ml.ipvz.fa.authservice.model.config.TokenConfig;
import ml.ipvz.fa.authservice.service.CacheService;
import ml.ipvz.fa.caching.CacheMono;
import ml.ipvz.fa.caching.util.CachingUtils;
import ml.ipvz.fa.userservice.client.UserServiceClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheServiceImpl implements CacheService {
    private final UserServiceClient userServiceClient;
    private final EventService eventService;
    private final CacheMono<Long, List<Permission>> userPermissionsCache;

    public CacheServiceImpl(TokenConfig config, UserServiceClient userServiceClient, EventService eventService) {
        this.userServiceClient = userServiceClient;
        this.eventService = eventService;
        this.userPermissionsCache = CachingUtils.ofMono(
                config.getValidDuration(),
                id -> userServiceClient.getPermissions(id).collectList()
        );
    }


    @PostConstruct
    private void postConstruct() {
        //on permissions update
        eventService.getUser().getPermissionUpdated().receive()
                .flatMap(event -> userServiceClient.getPermissions(event.getUserId()).collectList()
                        .doOnNext(p -> userPermissionsCache.update(event.getUserId(), p)))
                .subscribe();
    }

    public Mono<List<Permission>> getUserPermissions(Long id) {
        return userPermissionsCache.get(id);
    }
}

package space.ipvz.fa.caching;

import java.util.function.Function;

import com.github.benmanes.caffeine.cache.Cache;
import reactor.core.publisher.Mono;

public interface CacheMono<K, V> {
    void update(K key, V value);

    void invalidate(K key);

    Mono<V> get(K key);

    static <K, V> CacheMono<K, V> of(Cache<K, V> cache, Function<K, Mono<V>> fn) {
        return new CacheMono<>() {
            @Override
            public void update(K key, V value) {
                cache.put(key, value);
            }

            @Override
            public void invalidate(K key) {
                cache.invalidate(key);
            }

            @Override
            public Mono<V> get(K key) {
                V result = cache.getIfPresent(key);
                if (result != null) {
                    return Mono.just(result);
                } else {
                    return fn.apply(key).doOnNext(n -> cache.put(key, n));
                }
            }
        };
    }
}

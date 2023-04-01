package ml.ipvz.fa.caching.util;

import java.time.Duration;
import java.util.function.Function;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import ml.ipvz.fa.caching.CacheMono;
import reactor.core.publisher.Mono;

public class CachingUtils {
    private CachingUtils() {
    }

    public static <K, V> CacheMono<K, V> ofMono(Duration duration,
                                                Function<K, Mono<V>> fn) {
        final Cache<K, V> cache = Caffeine.newBuilder()
                .expireAfterWrite(duration)
                .recordStats()
                .build();

        return CacheMono.of(cache, fn);
    }
}

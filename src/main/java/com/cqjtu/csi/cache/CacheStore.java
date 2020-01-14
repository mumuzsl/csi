package com.cqjtu.csi.cache;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author mumu
 * @date 2020/1/14
 */
public interface CacheStore<K, V> {

    /**
     * Gets by cache key.
     *
     * @param key must not be null
     * @return cache value
     */
    @NonNull
    Optional<V> get(@NonNull K key);

    /**
     * Puts a non-expired cache.
     *
     * @param key   cache key must not be null
     * @param value cache value must not be null
     */
    void put(@NonNull K key, @NonNull V value);

    /**
     * Delete a key.
     *
     * @param key cache key must not be null
     */
    void delete(@NonNull K key);

}

package com.cqjtu.csi.cache;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mumu
 * @date 2020/1/14
 */
public class InMemoryCacheStore implements CacheStore<String, String> {

    private final static ConcurrentHashMap<String, String> CACHE_CONTAINER = new ConcurrentHashMap<>();

    @Override

    public Optional<String> get(String key) {
        return Optional.ofNullable(CACHE_CONTAINER.get(key));
    }

    @Override
    public void put(String key, String value) {
        CACHE_CONTAINER.put(key, value);
    }

    @Override
    public void delete(String key) {
        CACHE_CONTAINER.remove(key);
    }

}

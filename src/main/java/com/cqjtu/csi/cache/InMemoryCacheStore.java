package com.cqjtu.csi.cache;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCacheStore implements CacheStore<String, String> {

    private final static ConcurrentHashMap<String, String> cacheContainer = new ConcurrentHashMap<>();

    @Override

    public Optional<String> get(String key) {
        return Optional.ofNullable(cacheContainer.get(key));
    }

    @Override
    public void put(String key, String value) {
        cacheContainer.put(key, value);
    }

    @Override
    public void delete(String key) {
        cacheContainer.remove(key);
    }

}

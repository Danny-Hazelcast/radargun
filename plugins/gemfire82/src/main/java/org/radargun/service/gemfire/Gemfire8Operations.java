package org.radargun.service.gemfire;

import com.gemstone.gemfire.cache.Region;
import org.radargun.traits.BasicOperations;

public class Gemfire8Operations implements BasicOperations {


    private Gemfire8Service service;

    public Gemfire8Operations(Gemfire8Service service) {
        this.service = service;
    }

    @Override
    public <K, V> BasicOperations.Cache<K, V> getCache(String cacheName) {
        Region<K, V> region = service.getOrCreateRegion(cacheName);
        return new Gemfire8Cache<>(region);
    }

    public static class Gemfire8Cache<K, V> implements BasicOperations.Cache<K, V> {

        private Region<K, V> region;

        public Gemfire8Cache(Region<K, V> region) {
            this.region = region;
        }

        @Override
        public V get(K key) {
            return region.get(key);
        }

        @Override
        public boolean containsKey(K key) {
            return region.containsKey(key);
        }

        @Override
        public void put(K key, V value) {
            region.put(key, value);
        }

        @Override
        public V getAndPut(K key, V value) {
            return region.put(key, value);
        }

        @Override
        public boolean remove(K key) {
            return region.remove(key) != null;
        }

        @Override
        public V getAndRemove(K key) {
            return region.remove(key);
        }

        @Override
        public void clear() {
            region.clear();
        }
    }
}

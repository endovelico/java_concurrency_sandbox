package com.concurrency.sandbox.strip_locking;

import java.util.HashMap;
import java.util.Map;

public class StripedMap<K, V> {

    private final Map<K, V> map = new HashMap<>();
    private final StripLocking stripedLock;

    public StripedMap(int stripes) {
        this.stripedLock = new StripLocking(stripes);
    }

    public void put(K key, V value) {
        var lock = stripedLock.getLock(key);
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public V get(K key) {
        var lock = stripedLock.getLock(key);
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }
}

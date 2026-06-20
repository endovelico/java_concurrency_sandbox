package com.concurrency.sandbox.strip_locking;

import java.util.concurrent.locks.ReentrantLock;

// Hand over Hand Locking
public class StripLocking {

    private final ReentrantLock[] locks;

    public StripLocking(int stripes) {

        this.locks = new ReentrantLock[stripes];
        for (int i = 0; i < stripes; i++) {
            locks[i] = new ReentrantLock();
        }

    }

    public ReentrantLock getLock(Object key) {
        int hash = key.hashCode();
        int index = (hash & 0x7fffffff) % locks.length;
        return locks[index];
    }
}

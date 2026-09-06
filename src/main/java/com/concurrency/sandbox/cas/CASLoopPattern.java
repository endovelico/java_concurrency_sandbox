/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.cas;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.atomic.AtomicInteger;

public class CASLoopPattern {

    private final AtomicInteger value = new AtomicInteger(0);

    // CAS loop implementation of increment
    public int increment() {
        int oldValue;
        int newValue;

        while (true) {
            // 1. Read current value
            oldValue = value.get();

            // 2. Compute new value locally
            newValue = oldValue + 1;

            // 3. Try CAS update
            if (value.compareAndSet(oldValue, newValue)) {
                return newValue; // success
            }

            // 4. else: another thread interfered → retry
        }
    }

    public int get() {
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASLoopPattern counter = new CASLoopPattern();

        // Create multiple threads incrementing concurrently
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final value: " + counter.get());
    }
}

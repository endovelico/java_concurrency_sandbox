/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.volatile2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author endovelico
 */
public class VolatileDoesNotWorkHere {
     /*
     * ❌ CASE #1: increment
     */

    private volatile int counter = 0;

    public void increment() {
        counter++;
    }


    /*
     * ❌ CASE #2: check-then-act
     */

    private volatile boolean available = true;

    public void useResource() {

        if (available) {
            // Another thread can change `available`
            // after this check.

            doSomething();
        }
    }


    /*
     * ❌ CASE #3: multiple variables that must change
     *             as one atomic operation
     */

    private volatile int balance = 100;
    private volatile int transactionCount = 0;

    public void transaction() {

        balance -= 10;

        transactionCount++;
    }


    /*
     * ✅ Solution for the counter:
     *
     * AtomicInteger provides an atomic increment.
     */

    private final AtomicInteger safeCounter =
            new AtomicInteger(0);

    public void safeIncrement() {
        safeCounter.incrementAndGet();
    }


    private void doSomething() {
        System.out.println("Using resource");
    }
}

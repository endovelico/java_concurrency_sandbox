/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.virtual_thread_pitfalls;

import java.util.concurrent.CountDownLatch;

public class UnboundedConcurrency {

    public static void main(String[] args) throws InterruptedException {
        int tasks = 1_000_000;

        CountDownLatch started = new CountDownLatch(tasks);
        CountDownLatch release = new CountDownLatch(1);

        for (int i = 0; i < tasks; i++) {
            Thread.startVirtualThread(() -> {
                started.countDown();

                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        started.await();

        System.out.println("All virtual threads are running.");
        System.out.println("Press Ctrl+C to stop.");

        release.await();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.virtual_thread_pitfalls;

import java.util.concurrent.CountDownLatch;

public class SynchronousCode {

    public static void main(String[] args) throws InterruptedException {
        int tasks = 10_000;

        CountDownLatch latch = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    expensiveCalculation();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("All tasks finished.");
    }

    private static void expensiveCalculation() {
        long end = System.nanoTime() + 100_000_000L; // 100 ms

        while (System.nanoTime() < end) {
            // CPU-bound work
        }
    }
}
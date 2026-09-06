/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.countdown_latch;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 1; i <= 3; i++) {
            int workerId = i;

            Thread.startVirtualThread(() -> {
                System.out.println("Worker " + workerId + " started.");

                try {
                    Thread.sleep((long) (Math.random() * 3000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Worker " + workerId + " finished.");

                latch.countDown();
            });
        }

        System.out.println("Main thread waiting...");

        latch.await();

        System.out.println("All workers finished. Main thread continues.");
    }
}
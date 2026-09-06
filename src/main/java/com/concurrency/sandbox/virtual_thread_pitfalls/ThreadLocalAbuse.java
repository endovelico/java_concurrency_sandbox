/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.virtual_thread_pitfalls;

import java.util.concurrent.CountDownLatch;


public class ThreadLocalAbuse {

    private static final ThreadLocal<byte[]> DATA = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        int tasks = 10_000;

        CountDownLatch latch = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {
            Thread.startVirtualThread(() -> {
                // 1 MB stored per virtual thread
                DATA.set(new byte[1024 * 1024]);

                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    DATA.remove();
                    latch.countDown();
                }
            });
        }

        latch.await();
        System.out.println("Finished.");
    }
}
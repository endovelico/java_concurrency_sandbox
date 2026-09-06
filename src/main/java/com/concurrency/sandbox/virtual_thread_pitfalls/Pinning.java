/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.virtual_thread_pitfalls;

public class Pinning {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            Thread.startVirtualThread(() -> {

                synchronized (LOCK) {
                    try {
                        System.out.println(
                            "Sleeping: " + Thread.currentThread()
                        );

                        Thread.sleep(5_000);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

            });
        }

        Thread.sleep(30_000);
    }
}
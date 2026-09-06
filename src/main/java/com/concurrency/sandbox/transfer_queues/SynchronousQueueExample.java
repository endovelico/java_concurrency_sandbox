/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.transfer_queues;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

    public static void main(String[] args) throws Exception {

        SynchronousQueue<String> queue =
                new SynchronousQueue<>();

        Thread consumer = Thread.startVirtualThread(() -> {
            try {
                Thread.sleep(1000);

                String value = queue.take();

                System.out.println("Consumed: " + value);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Producer waiting...");

        queue.put("Hello");

        System.out.println("Producer released");

        consumer.join();
    }
}
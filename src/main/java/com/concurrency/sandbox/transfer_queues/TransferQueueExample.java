/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.transfer_queues;

import java.util.concurrent.TransferQueue;
import java.util.concurrent.LinkedTransferQueue;

public class TransferQueueExample {

    public static void main(String[] args) throws Exception {

        TransferQueue<String> queue =
                new LinkedTransferQueue<>();

        Thread consumer = Thread.startVirtualThread(() -> {
            try {
                String value = queue.take();
                System.out.println("Consumed: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        queue.transfer("Hello");

        System.out.println("Transfer completed");

        consumer.join();
    }
}
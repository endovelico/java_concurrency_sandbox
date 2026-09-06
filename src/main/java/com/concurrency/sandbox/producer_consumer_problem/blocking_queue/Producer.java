/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.producer_consumer_problem.blocking_queue;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author endovelico
 */
class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        int value = 1;

        try {
            while (true) {

                queue.put(value);    // Blocks if queue is full

                System.out.println(
                        Thread.currentThread().getName()
                        + " produced: "
                        + value);

                value++;

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

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
class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {

            while (true) {

                Integer value = queue.take(); // Blocks if queue is empty

                System.out.println(
                        Thread.currentThread().getName()
                        + " consumed: "
                        + value);

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

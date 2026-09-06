/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.blocking_queues;

import java.util.LinkedList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyProducerConsumer {

    private final Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 5;

    private final Object lock = new Object();

    // Producer
    class Producer implements Runnable {

        @Override
        public void run() {
            int value = 0;

            while (true) {
                synchronized (lock) {

                    // Wait while buffer is full
                    while (buffer.size() == CAPACITY) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    buffer.add(value);
                    System.out.println("Produced: " + value);
                    value++;

                    // Wake up a waiting consumer
                    lock.notify();
                }

                sleep(500);
            }
        }
    }

    // Consumer
    class Consumer implements Runnable {

        @Override
        public void run() {

            while (true) {
                synchronized (lock) {

                    // Wait while buffer is empty
                    while (buffer.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    int value = buffer.poll();
                    System.out.println("Consumed: " + value);

                    // Wake up a waiting producer
                    lock.notify();
                }

                sleep(1000);
            }
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }

    public static void main(String[] args) {
        new WaitNotifyProducerConsumer().start();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.producer_consumer_problem.wait_notify;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author endovelico
 */
class Buffer {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {

        // Wait while buffer is full
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(value);

        System.out.println(Thread.currentThread().getName()
                + " produced: " + value);

        // Wake up waiting consumers
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {

        // Wait while buffer is empty
        while (queue.isEmpty()) {
            wait();
        }

        int value = queue.remove();

        System.out.println(Thread.currentThread().getName()
                + " consumed: " + value);

        // Wake up waiting producers
        notifyAll();

        return value;
    }
}
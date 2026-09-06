/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.producer_consumer_problem.reentrant_condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author endovelico
 */
class Buffer {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public void produce(int value) throws InterruptedException {

        lock.lock();

        try {

            while (queue.size() == capacity) {
                notFull.await();
            }

            queue.add(value);

            System.out.println(Thread.currentThread().getName()
                    + " produced: " + value);

            // Wake one waiting consumer
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    public int consume() throws InterruptedException {

        lock.lock();

        try {

            while (queue.isEmpty()) {
                notEmpty.await();
            }

            int value = queue.remove();

            System.out.println(Thread.currentThread().getName()
                    + " consumed: " + value);

            // Wake one waiting producer
            notFull.signal();

            return value;

        } finally {
            lock.unlock();
        }
    }
}

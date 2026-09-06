/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.condition_variable;

/**
 *
 * @author endovelico
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableExample {

    private final Queue<String> queue = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock();

    // Condition representing "queue is not empty"
    private final Condition notEmpty = lock.newCondition();

    public void produce(String item) {
        lock.lock();
        try {
            queue.add(item);
            System.out.println("Produced: " + item);

            // Wake up one waiting consumer
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    public String consume() throws InterruptedException {
        lock.lock();
        try {

            // Always use while, never if
            while (queue.isEmpty()) {
                System.out.println("Consumer waiting...");
                notEmpty.await();
            }

            String item = queue.remove();
            System.out.println("Consumed: " + item);
            return item;

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ConditionVariableExample example = new ConditionVariableExample();

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    example.consume();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {
            }
        });

        Thread producer = new Thread(() -> {
            try {
                int i = 1;
                while (true) {
                    Thread.sleep(2000);
                    example.produce("Ball-" + i++);
                }
            } catch (InterruptedException ignored) {
            }
        });

        consumer.start();
        producer.start();
    }
}
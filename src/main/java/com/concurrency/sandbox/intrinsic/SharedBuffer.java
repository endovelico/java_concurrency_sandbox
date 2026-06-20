/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.intrinsic;

/**
 *
 * @author endovelico
 */
class SharedBuffer {

    private int data;
    private boolean hasData = false;

    // PRODUCER puts data
    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait(); // releases lock and waits
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        data = value;
        hasData = true;
        System.out.println("Produced: " + value);

        notifyAll(); // wake up consumer(s)
    }

    // CONSUMER takes data
    public synchronized int consume() {
        while (!hasData) {
            try {
                wait(); // releases lock and waits
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        hasData = false;
        System.out.println("Consumed: " + data);

        notifyAll(); // wake up producer(s)
        return data;
    }
}

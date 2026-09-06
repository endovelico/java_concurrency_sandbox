/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.synchronous_queue;

public class MySynchronousQueue<T> {

    private T item;
    private boolean hasItem = false;

    public synchronized void put(T value)
            throws InterruptedException {

        // Wait until previous item has been consumed
        while (hasItem) {
            wait();
        }

        // Place item for consumer
        item = value;
        hasItem = true;

        // Wake up consumers
        notifyAll();

        // Wait until consumer takes the item
        while (hasItem) {
            wait();
        }
    }

    public synchronized T take()
            throws InterruptedException {

        // Wait until producer provides an item
        while (!hasItem) {
            wait();
        }

        T value = item;

        item = null;
        hasItem = false;

        // Wake up producer
        notifyAll();

        return value;
    }
}
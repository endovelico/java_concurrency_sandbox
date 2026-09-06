/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.single_update_queue;

/**
 *
 * @author endovelico
 */
import java.util.LinkedList;
import java.util.Queue;

public class SingleWaitingQueueLock {

    private final Queue<WaitNode> queue = new LinkedList<>();

    private boolean locked = false;

    public synchronized void lock() throws InterruptedException {

        WaitNode node = new WaitNode(Thread.currentThread());

        queue.offer(node);

        while (locked || queue.peek() != node) {
            wait();
        }

        queue.poll();

        locked = true;
    }

    public synchronized void unlock() {

        locked = false;

        notifyAll();
    }
}

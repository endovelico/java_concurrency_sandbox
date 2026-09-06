/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.single_update_queue;

/**
 *
 * @author endovelico
 */
public class Demo {

    public static void main(String[] args) {

        SingleWaitingQueueLock lock =
                new SingleWaitingQueueLock();

        Runnable task = () -> {

            try {

                lock.lock();

                System.out.println(
                        Thread.currentThread().getName()
                        + " acquired");

                Thread.sleep(1000);

                System.out.println(
                        Thread.currentThread().getName()
                        + " releasing");

                lock.unlock();

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 1; i <= 5; i++) {
            new Thread(task, "T" + i).start();
        }
    }
}

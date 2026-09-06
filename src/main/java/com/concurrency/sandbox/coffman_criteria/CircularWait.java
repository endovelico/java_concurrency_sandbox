/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.coffman_criteria;

/**
 *
 * @author endovelico
 */
class CircularWait {
    private final Object resourceA = new Object();
    private final Object resourceB = new Object();

    public void startDeadlock() {

        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Thread 1 has Resource A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resourceB) {
                    System.out.println("Thread 1 has Resource B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("Thread 2 has Resource B");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resourceA) {
                    System.out.println("Thread 2 has Resource A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
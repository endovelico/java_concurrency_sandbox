/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.coffman_criteria;

/**
 *
 * @author endovelico
 */
class NoPreemption {
    private final Object lock = new Object();

    public void useResource() {
        synchronized (lock) {
            System.out.println("Resource acquired.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Resource released.");
        }
    }
}

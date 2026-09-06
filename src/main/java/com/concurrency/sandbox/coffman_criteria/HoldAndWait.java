/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.coffman_criteria;

/**
 *
 * @author endovelico
 */
class HoldAndWait {
    private final Object resourceA = new Object();
    private final Object resourceB = new Object();

    public void execute() {
        synchronized (resourceA) {
            System.out.println("Holding Resource A...");
            synchronized (resourceB) {
                System.out.println("Acquired Resource B.");
            }
        }
    }
}
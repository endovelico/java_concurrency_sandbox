/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.synchronize;

public class GlobalLockExample {

    private final String workerName;

    public GlobalLockExample(String workerName) {
        this.workerName = workerName;
    }

    public void accessGlobalResource() {

        synchronized (GlobalLockExample.class) {

            System.out.println(
                    workerName
                            + " acquired the global lock"
            );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(
                    workerName
                            + " released the global lock"
            );
        }
    }
}
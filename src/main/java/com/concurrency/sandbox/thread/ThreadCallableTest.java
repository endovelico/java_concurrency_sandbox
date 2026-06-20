/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.thread;

import java.util.concurrent.Callable;

/**
 *
 * @author endovelico
 */
public class ThreadCallableTest implements Callable<Integer> {
    
    private int number;

    public ThreadCallableTest(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Executing task in thread: " + Thread.currentThread().getName());

        // Simulate work
        int result = 0;
        for (int i = 1; i <= number; i++) {
            result += i;
        }

        return result; // returning result
    }
}

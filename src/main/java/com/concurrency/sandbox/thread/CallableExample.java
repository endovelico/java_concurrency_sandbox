/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author endovelico
 */
public class CallableExample {
    public static void main(String[] args) {

        // Step 2: Create thread pool
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Step 3: Submit Callable task
        Callable<Integer> task = new ThreadCallableTest(10);
        Future<Integer> future = executor.submit(task);

        try {
            // Step 4: Get result (blocks until done)
            Integer result = future.get();
            System.out.println("Result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Step 5: Shutdown executor
        executor.shutdown();
    }
}
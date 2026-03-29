package com.concurrency.sandbox.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Callable example
public class CallableTask implements Callable<String> {

    private String name;

    public CallableTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {

        System.out.println(name + " started running on thread: " + Thread.currentThread().getName());
        Thread.sleep(1000); // simulate some work
        System.out.println(name + " finished running.");
        return name + " result";
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CallableTask task1 = new CallableTask("Task1");
        CallableTask task2 = new CallableTask("Task2");

        Future<String> future1 = executor.submit(task1);
        Future<String> future2 = executor.submit(task2);

        try {

            System.out.println("Result from task1: " + future1.get());
            System.out.println("Result from task2: " + future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}

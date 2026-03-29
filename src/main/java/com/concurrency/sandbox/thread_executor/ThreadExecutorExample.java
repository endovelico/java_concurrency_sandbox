package com.concurrency.sandbox.thread_executor;

import java.util.concurrent.*;
import java.util.Random;

public class ThreadExecutorExample {

    private ThreadPoolExecutor executor;

    public ThreadExecutorExample(int corePoolSize, int maximumPoolSize) {

        // Create a ThreadPoolExecutor
        executor = new ThreadPoolExecutor(
                corePoolSize,          // core threads
                maximumPoolSize,       // max threads
                60,                    // idle thread keep-alive time
                TimeUnit.SECONDS,      // time unit for keep-alive
                new LinkedBlockingQueue<Runnable>() // task queue
        );
    }

    // A simple task that simulates work
    public Runnable createTask(String name, int duration) {
        return () -> {
            System.out.println("Task " + name + " started, will take " + duration + " seconds.");
            try {
                Thread.sleep(duration * 1000L); // simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task " + name + " finished.");
        };
    }

    public void runTasks() {
        String[] taskNames = {"A", "B", "C", "D"};
        Random random = new Random();

        // Submit tasks to the executor
        for (String name : taskNames) {
            int duration = random.nextInt(5) + 1; // random 1-5 seconds
            executor.submit(createTask(name, duration));
        }

        // Shutdown the executor after all tasks are submitted
        executor.shutdown();
        try {
            // Wait for all tasks to complete
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("All tasks completed.");
    }

    public static void main(String[] args) {
        ThreadExecutorExample test = new ThreadExecutorExample(2, 4);
        test.runTasks();
    }
}

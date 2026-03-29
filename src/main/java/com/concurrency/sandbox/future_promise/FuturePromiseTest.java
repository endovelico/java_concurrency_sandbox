package com.concurrency.sandbox.future_promise;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FuturePromiseTest {

    // A simple task that returns a result
    public static String task(String name, int duration) {
        System.out.println("Task " + name + " started, will take " + duration + " seconds.");
        try {
            Thread.sleep(duration * 1000L); // simulate blocking work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String result = "Task " + name + " finished.";
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        String[] taskNames = {"A", "B", "C", "D"};
        Random random = new Random();

        CompletableFuture<String>[] futures = new CompletableFuture[taskNames.length];

        // Create asynchronous tasks (Promises)
        for (int i = 0; i < taskNames.length; i++) {
            int duration = random.nextInt(5) + 1;
            String name = taskNames[i];

            futures[i] = CompletableFuture.supplyAsync(() -> task(name, duration));
        }

        // Combine all futures and wait for them to complete
        CompletableFuture<Void> all = CompletableFuture.allOf(futures);

        // Block until all are done
        all.join();

        // Collect results
        for (CompletableFuture<String> f : futures) {
            try {
                System.out.println("Result: " + f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All tasks completed.");
    }
}

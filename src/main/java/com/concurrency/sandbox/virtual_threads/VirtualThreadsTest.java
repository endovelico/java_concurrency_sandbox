package com.concurrency.sandbox.virtual_threads;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class VirtualThreadsTest {

    // A simple task method
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

        Future<String>[] futures = new Future[taskNames.length];

        // Create virtual threads
        for (int i = 0; i < taskNames.length; i++) {
            int duration = random.nextInt(5) + 1; // 1-5 seconds
            String name = taskNames[i];

            futures[i] = (Future<String>) Thread.ofVirtual().start(() -> task(name, duration));
        }

        // Wait for all tasks to complete
        for (Future<String> f : futures) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All tasks completed.");
    }
}

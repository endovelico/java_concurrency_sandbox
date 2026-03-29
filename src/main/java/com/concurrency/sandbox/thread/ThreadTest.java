package com.concurrency.sandbox.thread;

import java.util.Random;

public class ThreadTest {

    // A simple task that simulates work
    public static class Task extends Thread {
        private String name;
        private int duration;

        public Task(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }

        @Override
        public void run() {
            System.out.println("Task " + name + " started, will take " + duration + " seconds.");
            try {
                Thread.sleep(duration * 1000L); // simulate work
            } catch (InterruptedException e) {
                System.out.println("Task " + name + " was interrupted.");
                Thread.currentThread().interrupt();
            }
            System.out.println("Task " + name + " finished.");
        }
    }

    public static void main(String[] args) {
        String[] taskNames = {"A", "B", "C", "D"};
        Random random = new Random();

        Thread[] threads = new Thread[taskNames.length];

        // Create and start threads
        for (int i = 0; i < taskNames.length; i++) {
            int duration = random.nextInt(5) + 1; // random 1-5 seconds
            threads[i] = new Task(taskNames[i], duration);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All tasks completed.");
    }
}

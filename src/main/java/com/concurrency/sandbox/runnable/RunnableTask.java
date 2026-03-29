package com.concurrency.sandbox.runnable;

// Runnable example
public class RunnableTask implements Runnable {

    private String name;

    public RunnableTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " started running on thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // simulate some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " finished running.");
    }

    public static void main(String[] args) {
        RunnableTask task1 = new RunnableTask("Task1");
        RunnableTask task2 = new RunnableTask("Task2");

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();
    }
}

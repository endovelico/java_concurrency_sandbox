/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.thread_local;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> REQUEST_ID =
            ThreadLocal.withInitial(() -> -1);

    private static final ExecutorService POOL =
            Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {

        System.out.println("=======================================");
        System.out.println("1. Every thread gets its own value");
        System.out.println("=======================================");

        Thread t1 = new Thread(() -> {

            REQUEST_ID.set(100);

            sleep();

            System.out.printf("[%s] %d%n",
                    Thread.currentThread().getName(),
                    REQUEST_ID.get());

        }, "Thread-A");

        Thread t2 = new Thread(() -> {

            REQUEST_ID.set(200);

            sleep();

            System.out.printf("[%s] %d%n",
                    Thread.currentThread().getName(),
                    REQUEST_ID.get());

        }, "Thread-B");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println();

        //----------------------------------------------------

        System.out.println("=======================================");
        System.out.println("2. Initial value");
        System.out.println("=======================================");

        Thread t3 = new Thread(() ->

                System.out.printf("[%s] %d%n",
                        Thread.currentThread().getName(),
                        REQUEST_ID.get())

        );

        t3.start();
        t3.join();

        System.out.println();

        //----------------------------------------------------

        System.out.println("=======================================");
        System.out.println("3. remove()");
        System.out.println("=======================================");

        Thread t4 = new Thread(() -> {

            REQUEST_ID.set(999);

            System.out.println("Before remove = "
                    + REQUEST_ID.get());

            REQUEST_ID.remove();

            System.out.println("After remove = "
                    + REQUEST_ID.get());

        });

        t4.start();
        t4.join();

        System.out.println();

        //----------------------------------------------------

        System.out.println("=======================================");
        System.out.println("4. Thread Pool Reuse");
        System.out.println("=======================================");

        POOL.submit(() -> {

            REQUEST_ID.set(123);

            System.out.println(
                    "Task 1 = " + REQUEST_ID.get());

            // Forgot remove()

        }).get();

        POOL.submit(() ->

                System.out.println(
                        "Task 2 = " + REQUEST_ID.get())

        ).get();

        System.out.println();

        //----------------------------------------------------

        System.out.println("=======================================");
        System.out.println("5. Correct cleanup");
        System.out.println("=======================================");

        POOL.submit(() -> {

            try {

                REQUEST_ID.set(456);

                System.out.println(
                        "Task 3 = " + REQUEST_ID.get());

            } finally {

                REQUEST_ID.remove();
            }

        }).get();

        POOL.submit(() ->

                System.out.println(
                        "Task 4 = " + REQUEST_ID.get())

        ).get();

        //----------------------------------------------------

        POOL.shutdown();
        POOL.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void sleep() {

        try {

            Thread.sleep(500);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
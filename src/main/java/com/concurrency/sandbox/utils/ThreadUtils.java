package com.concurrency.sandbox.utils;

public class ThreadUtils {

    public static void log(String message) {
        System.out.println(
                Thread.currentThread().getName() + " | " + message
        );
    }
}

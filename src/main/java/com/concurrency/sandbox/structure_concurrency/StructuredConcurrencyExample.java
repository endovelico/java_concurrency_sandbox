package com.concurrency.sandbox.structure_concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyExample {
    public static void main(String[] args) throws Exception {

        try (var scope = new StructuredTaskScope.ShutdownOnFailure<Void>()) {
            var future1 = scope.fork(() -> {
                Thread.sleep(1000);
                System.out.println("Task 1 completed");
                return 10;
            });

            var future2 = scope.fork(() -> {
                Thread.sleep(500);
                System.out.println("Task 2 completed");
                return 20;
            });

            scope.join();          // Wait for all tasks
            scope.throwIfFailed(); // Propagate exception if any

            int total = future1.resultNow() + future2.resultNow();
            System.out.println("Total result: " + total);
        }
    }
}
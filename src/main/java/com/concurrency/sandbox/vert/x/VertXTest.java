package com.concurrency.sandbox.vert.x;


import io.vertx.core.Vertx;
import io.vertx.core.Future;
import io.vertx.core.Promise;

import java.util.Random;

public class VertXTest {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        String[] taskNames = {"A", "B", "C", "D"};
        Random random = new Random();

        for (String name : taskNames) {
            int duration = random.nextInt(5) + 1; // random 1-5 seconds

            vertx.executeBlocking(promise -> {
                System.out.println("Task " + name + " started, will take " + duration + " seconds.");
                try {
                    Thread.sleep(duration * 1000L); // simulate blocking work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + name + " finished.");
                promise.complete(name);
            }, res -> {
                if (res.succeeded()) {
                    System.out.println("Task " + res.result() + " callback completed.");
                } else {
                    System.out.println("Task " + name + " failed: " + res.cause());
                }
            });
        }

        // Give enough time for all tasks to finish before shutting down Vert.x
        vertx.setTimer(10000, id -> vertx.close());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.virtual_threads_plus_scoped_values;

/**
 *
 * @author endovelico
 */
import java.lang.ScopedValue;
import java.util.concurrent.StructuredTaskScope;

public class ExampleVTSV {

    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> USER = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {

        // Simulate one incoming HTTP request
        ScopedValue.runWhere(REQUEST_ID, "REQ-42", () ->
            ScopedValue.runWhere(USER, "alice", () -> {

                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

                    scope.fork(ExampleVTSV::loadOrders);
                    scope.fork(ExampleVTSV::loadRecommendations);
                    scope.fork(ExampleVTSV::auditRequest);

                    scope.join();
                    scope.throwIfFailed();
                }

            })
        );
    }

    static void loadOrders() {
        log("Loading orders...");
        sleep();
        log("Orders loaded.");
    }

    static void loadRecommendations() {
        log("Loading recommendations...");
        sleep();
        log("Recommendations loaded.");
    }

    static void auditRequest() {
        log("Writing audit record...");
        sleep();
        log("Audit complete.");
    }

    static void log(String message) {
        System.out.printf(
            "[%s] user=%s thread=%s : %s%n",
            REQUEST_ID.get(),
            USER.get(),
            Thread.currentThread(),
            message
        );
    }

    static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
    }
}
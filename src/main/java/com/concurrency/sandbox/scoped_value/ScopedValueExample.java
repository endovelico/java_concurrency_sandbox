/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.scoped_value;


import java.lang.ScopedValue;

public class ScopedValueExample {

    private static final ScopedValue<String> USER =
            ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {

        System.out.println("=======================================");
        System.out.println("1. Unbound ScopedValue");
        System.out.println("=======================================");

        try {
            System.out.println(USER.get());
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("2. Basic Binding");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Alice", () -> {

            System.out.println("Inside scope : " + USER.get());

        });

        try {
            USER.get();
        } catch (Exception e) {
            System.out.println("Outside scope: " + e.getClass().getSimpleName());
        }

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("3. Nested Scopes");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Alice", () -> {

            System.out.println(USER.get());

            ScopedValue.runWhere(USER, "Bob", () -> {

                System.out.println(USER.get());

            });

            System.out.println(USER.get());

        });

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("4. Child Threads");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Charlie", () -> {

            Thread thread = Thread.ofVirtual().start(() -> {

                System.out.println(
                        Thread.currentThread() + " -> " + USER.get());

            });

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("5. Shadowing");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Outer", () -> {

            System.out.println(USER.get());

            ScopedValue.runWhere(USER, "Inner", () -> {

                System.out.println(USER.get());

            });

            System.out.println(USER.get());

        });

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("6. Automatic Cleanup");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Temporary", () -> {

            System.out.println("Inside : " + USER.get());

        });

        System.out.println("Scope exited.");

        try {

            USER.get();

        } catch (Exception e) {

            System.out.println("Value automatically removed.");
        }

        //------------------------------------------------------

        System.out.println("\n=======================================");
        System.out.println("7. Cannot Modify");
        System.out.println("=======================================");

        ScopedValue.runWhere(USER, "Alice", () -> {

            System.out.println(USER.get());

            // Impossible:
            //
            // USER.set(...)
            //
            // ScopedValue is immutable.

        });

        //------------------------------------------------------

        System.out.println("\nDemo complete.");
    }
}
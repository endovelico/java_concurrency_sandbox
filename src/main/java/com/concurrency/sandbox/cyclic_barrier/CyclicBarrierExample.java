/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.cyclic_barrier;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(
                3,
                () -> System.out.println("\nAll runners reached the checkpoint!\n")
        );

        for (int i = 1; i <= 3; i++) {
            int runnerId = i;

            Thread.startVirtualThread(() -> {

                for (int lap = 1; lap <= 3; lap++) {

                    System.out.println("Runner " + runnerId +
                            " running lap " + lap);

                    try {
                        Thread.sleep((long) (Math.random() * 3000));

                        System.out.println("Runner " + runnerId +
                                " finished lap " + lap);

                        barrier.await();

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    } catch (BrokenBarrierException e) {
                        return;
                    }
                }

                System.out.println("Runner " + runnerId + " finished race.");
            });
        }
    }
}
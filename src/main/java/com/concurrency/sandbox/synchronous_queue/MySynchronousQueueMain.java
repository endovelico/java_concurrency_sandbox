/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.synchronous_queue;

/**
 *
 * @author endovelico
 */
public class MySynchronousQueueMain {
    

    public static void main(String[] args) {

        MySynchronousQueue<String> queue =
                new MySynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer: putting item");

                queue.put("Hello");

                System.out.println("Producer: item consumed");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(2000);

                System.out.println("Consumer: taking item");

                String value = queue.take();

                System.out.println("Consumer received: " + value);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.lmax;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleDisruptor<T> {

    private final Object[] ringBuffer;
    private final int mask;

    // Next sequence available for the producer to claim
    private final AtomicLong nextSequence = new AtomicLong(0);

    // Highest sequence that has been published
    private final AtomicLong publishedSequence = new AtomicLong(-1);

    // Sequence consumed by the consumer
    private final AtomicLong consumedSequence = new AtomicLong(-1);

    public SimpleDisruptor(int bufferSize) {
        if (Integer.bitCount(bufferSize) != 1) {
            throw new IllegalArgumentException(
                "Buffer size must be a power of two"
            );
        }

        this.ringBuffer = new Object[bufferSize];
        this.mask = bufferSize - 1;
    }

    /**
     * Publishes an event into the ring buffer.
     */
    public void publish(T event) {

        // Claim the next sequence
        long sequence = nextSequence.getAndIncrement();

        // Wait if the consumer has fallen behind and
        // we would overwrite an unconsumed event.
        while (sequence - consumedSequence.get() >= ringBuffer.length) {
            Thread.onSpinWait();
        }

        int index = (int) (sequence & mask);

        ringBuffer[index] = event;

        // Publish the event.
        publishedSequence.set(sequence);
    }

    /**
     * Starts a single consumer.
     */
    public Thread startConsumer(EventHandler<T> handler) {

        Thread consumer = Thread.startVirtualThread(() -> {

            long next = consumedSequence.get() + 1;

            while (!Thread.currentThread().isInterrupted()) {

                // Wait for the producer to publish this sequence.
                while (publishedSequence.get() < next) {
                    Thread.onSpinWait();

                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                }

                int index = (int) (next & mask);

                @SuppressWarnings("unchecked")
                T event = (T) ringBuffer[index];

                try {
                    handler.onEvent(event, next);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                consumedSequence.set(next);

                next++;
            }
        });

        return consumer;
    }

    @FunctionalInterface
    public interface EventHandler<T> {
        void onEvent(T event, long sequence) throws Exception;
    }
}
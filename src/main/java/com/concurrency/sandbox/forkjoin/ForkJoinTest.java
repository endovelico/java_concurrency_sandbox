package com.concurrency.sandbox.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;

public class ForkJoinTest {

    // Threshold for splitting tasks
    private static final int THRESHOLD = 10_000;

    // The RecursiveTask that computes sum
    static class SumTask extends RecursiveTask<Long> {

        private final long[] array;
        private final int start;
        private final int end;

        public SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                // Base case: sum sequentially
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Recursive case: split task
                int mid = start + length / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                leftTask.fork(); // run left task asynchronously
                long rightResult = rightTask.compute(); // compute right task in current thread
                long leftResult = leftTask.join(); // wait for left task to finish

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {

        int size = 10_000_000;
        long[] numbers = new long[size];

        // Fill array with random numbers
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(100);
        }

        // Create ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create the main task
        SumTask task = new SumTask(numbers, 0, numbers.length);

        // Submit task to pool and get result
        long startTime = System.currentTimeMillis();
        long total = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Total sum: " + total);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

        pool.shutdown();
    }
}

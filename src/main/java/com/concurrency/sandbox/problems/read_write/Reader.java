/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.problems.read_write;

/**
 *
 * @author endovelico
 */
public class Reader extends Thread {

    private final Object lock;
    private final int iterations;

    public Reader(String name, Object lock, int iterations) {
        super(name);
        this.lock = lock;
        this.iterations = iterations;
    }

    @Override
    public void run() {

        try {

            for (int i = 0; i < iterations; i++) {

                if (lock instanceof ReadersPreference rp) {
                    rp.startRead(getName());
                    Thread.sleep(500);
                    rp.endRead(getName());

                } else if (lock instanceof WritersPreference wp) {
                    wp.startRead(getName());
                    Thread.sleep(500);
                    wp.endRead(getName());

                } else if (lock instanceof FairReadersWriters fp) {
                    fp.startRead(getName());
                    Thread.sleep(500);
                    fp.endRead(getName());
                }

                Thread.sleep(300);
            }

        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
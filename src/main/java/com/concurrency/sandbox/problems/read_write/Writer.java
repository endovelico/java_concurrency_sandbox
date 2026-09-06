/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.problems.read_write;

/**
 *
 * @author endovelico
 */
public class Writer extends Thread {

    private final Object lock;
    private final int iterations;

    public Writer(String name, Object lock, int iterations) {
        super(name);
        this.lock = lock;
        this.iterations = iterations;
    }

    @Override
    public void run() {

        try {

            for (int i = 0; i < iterations; i++) {

                if (lock instanceof ReadersPreference rp) {
                    rp.startWrite(getName());
                    Thread.sleep(1000);
                    rp.endWrite(getName());

                } else if (lock instanceof WritersPreference wp) {
                    wp.startWrite(getName());
                    Thread.sleep(1000);
                    wp.endWrite(getName());

                } else if (lock instanceof FairReadersWriters fp) {
                    fp.startWrite(getName());
                    Thread.sleep(1000);
                    fp.endWrite(getName());
                }

                Thread.sleep(700);
            }

        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
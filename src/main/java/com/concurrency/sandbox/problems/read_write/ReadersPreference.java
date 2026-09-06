/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.problems.read_write;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.Semaphore;

public class ReadersPreference {

    private int readCount = 0;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore rwMutex = new Semaphore(1);

    public void startRead(String reader) throws InterruptedException {
        mutex.acquire();
        readCount++;

        if (readCount == 1) {
            rwMutex.acquire(); // First reader locks writers
        }

        mutex.release();

        System.out.println(reader + " is READING");
    }

    public void endRead(String reader) throws InterruptedException {
        mutex.acquire();

        readCount--;

        System.out.println(reader + " finished reading");

        if (readCount == 0) {
            rwMutex.release();
        }

        mutex.release();
    }

    public void startWrite(String writer) throws InterruptedException {
        rwMutex.acquire();

        System.out.println(writer + " is WRITING");
    }

    public void endWrite(String writer) {
        System.out.println(writer + " finished writing");
        rwMutex.release();
    }
}
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

public class WritersPreference {

    private int readCount = 0;
    private int writeCount = 0;

    private final Semaphore resource = new Semaphore(1);
    private final Semaphore readTry = new Semaphore(1);
    private final Semaphore rMutex = new Semaphore(1);
    private final Semaphore wMutex = new Semaphore(1);

    public void startRead(String reader) throws InterruptedException {

        readTry.acquire();

        rMutex.acquire();
        readCount++;

        if (readCount == 1)
            resource.acquire();

        rMutex.release();
        readTry.release();

        System.out.println(reader + " is READING");
    }

    public void endRead(String reader) throws InterruptedException {

        rMutex.acquire();

        readCount--;

        System.out.println(reader + " finished reading");

        if (readCount == 0)
            resource.release();

        rMutex.release();
    }

    public void startWrite(String writer) throws InterruptedException {

        wMutex.acquire();

        writeCount++;

        if (writeCount == 1)
            readTry.acquire();

        wMutex.release();

        resource.acquire();

        System.out.println(writer + " is WRITING");
    }

    public void endWrite(String writer) throws InterruptedException {

        System.out.println(writer + " finished writing");

        resource.release();

        wMutex.acquire();

        writeCount--;

        if (writeCount == 0)
            readTry.release();

        wMutex.release();
    }
}
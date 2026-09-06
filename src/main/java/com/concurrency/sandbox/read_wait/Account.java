/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_wait;

/**
 *
 * @author endovelico
 */
import java.util.concurrent.locks.ReentrantReadWriteLock;


class Account {

    private int balance;

    private final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();


    public Account(int balance) {
        this.balance = balance;
    }


    // READ-WAIT operation
    public int readBalance() {


        lock.readLock().lock();

        try {

            System.out.println(
                Thread.currentThread().getName()
                + " reading balance"
            );


            return balance;

        }
        finally {

            lock.readLock().unlock();
        }
    }



    // WRITE operation
    public void updateBalance(int amount) {


        lock.writeLock().lock();


        try {

            System.out.println(
                Thread.currentThread().getName()
                + " updating balance"
            );


            Thread.sleep(1000);

            balance += amount;


        }
        catch(Exception e) {

        }
        finally {

            lock.writeLock().unlock();

        }
    }
}
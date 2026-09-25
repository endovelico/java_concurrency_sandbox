/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.synchronize;

public class InstanceLockExample {

    private int balance;

    public InstanceLockExample(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {

        synchronized (this) {
            balance += amount;

            System.out.println(
                    Thread.currentThread().getName()
                            + " deposited "
                            + amount
                            + ", balance = "
                            + balance
            );
        }
    }

    public int getBalance() {
        synchronized (this) {
            return balance;
        }
    }
}
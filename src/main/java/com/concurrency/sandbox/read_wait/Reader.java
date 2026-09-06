/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_wait;

/**
 *
 * @author endovelico
 */
class Reader implements Runnable {


    private final Account account;


    Reader(Account account) {
        this.account = account;
    }


    public void run() {


        int value =
            account.readBalance();


        System.out.println(
            "Read value = " + value
        );
    }
}
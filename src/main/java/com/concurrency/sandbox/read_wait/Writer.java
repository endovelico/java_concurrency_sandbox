/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_wait;

/**
 *
 * @author endovelico
 */
class Writer implements Runnable {


    private final Account account;


    Writer(Account account) {
        this.account = account;
    }


    public void run() {

        account.updateBalance(50);
    }
}

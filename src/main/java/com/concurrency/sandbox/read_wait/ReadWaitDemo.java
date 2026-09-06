/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_wait;

/**
 *
 * @author endovelico
 */
public class ReadWaitDemo {


    public static void main(String[] args)
            throws Exception {


        Account account =
                new Account(100);


        Thread writer =
                new Thread(
                    new Writer(account),
                    "Writer"
                );


        Thread reader =
                new Thread(
                    new Reader(account),
                    "Reader"
                );


        writer.start();

        Thread.sleep(100);

        reader.start();


        writer.join();
        reader.join();
    }
}

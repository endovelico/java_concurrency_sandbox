/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_restart;

/**
 *
 * @author endovelico
 */
public class ReadRestartDemo {


    public static void main(String[] args)
            throws Exception {


        VersionedData data =
                new VersionedData(10);


        Thread reader =
                new Thread(
                    new Reader(data)
                );


        Thread writer =
                new Thread(
                    new Writer(data)
                );


        reader.start();
        writer.start();


        reader.join();
        writer.join();
    }
}

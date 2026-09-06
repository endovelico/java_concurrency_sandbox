/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_restart;

/**
 *
 * @author endovelico
 */
class Writer implements Runnable {


    private final VersionedData data;


    public Writer(VersionedData data) {
        this.data = data;
    }


    @Override
    public void run() {


        try {
            Thread.sleep(300);
        }
        catch(Exception e) {
        }


        data.update(20);
    }
}

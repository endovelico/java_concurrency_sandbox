/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_restart;

/**
 *
 * @author endovelico
 */
class Snapshot {

    final int value;
    final long version;


    public Snapshot(
            int value,
            long version) {

        this.value = value;
        this.version = version;
    }
}
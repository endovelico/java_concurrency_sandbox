/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.problems.read_write;

/**
 *
 * @author endovelico
 */
public class Main {

    public static void main(String[] args) {

        ReadersPreference lock = new ReadersPreference();

        // Uncomment to test another strategy
        // WritersPreference lock = new WritersPreference();
        // FairReadersWriters lock = new FairReadersWriters();

        new Reader("Reader-1", lock, 3).start();
        new Reader("Reader-2", lock, 3).start();
        new Reader("Reader-3", lock, 3).start();

        new Writer("Writer-1", lock, 2).start();
        new Writer("Writer-2", lock, 2).start();
    }
}
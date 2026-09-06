/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.coffman_criteria;

/**
 *
 * @author endovelico
 */
class MutualExclusion {
    
    public synchronized void print(String document) {
        System.out.println("Printing: " + document);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
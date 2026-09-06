/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.single_update_queue;

/**
 *
 * @author endovelico
 */
public class WaitNode {

    final Thread thread;

    public WaitNode(Thread thread) {
        this.thread = thread;
    }

    @Override
    public String toString() {
        return thread.getName();
    }
}

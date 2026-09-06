/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_restart;

/**
 *
 * @author endovelico
 */
class VersionedData {

    private int value;
    private long version;


    public VersionedData(int value) {
        this.value = value;
        this.version = 0;
    }


    // Writer updates value
    public synchronized void update(int newValue) {

        value = newValue;
        version++;

        System.out.println(
            "Updated value=" + value +
            " version=" + version
        );
    }


    // Read snapshot
    public synchronized Snapshot read() {

        return new Snapshot(
            value,
            version
        );
    }


    // Validate that nothing changed
    public synchronized boolean validate(
            long expectedVersion) {

        return version == expectedVersion;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.bloom_filter;

import java.util.BitSet;

public class BloomFilter {

    private final BitSet bits;
    private final int size;
    private final int numberOfHashFunctions;

    public BloomFilter(int size, int numberOfHashFunctions) {
        this.size = size;
        this.numberOfHashFunctions = numberOfHashFunctions;
        this.bits = new BitSet(size);
    }

    public void add(String value) {

        for (int i = 0; i < numberOfHashFunctions; i++) {

            int hash = hash(value, i);

            int index = Math.floorMod(hash, size);

            bits.set(index);
        }
    }

    public boolean mightContain(String value) {

        for (int i = 0; i < numberOfHashFunctions; i++) {

            int hash = hash(value, i);

            int index = Math.floorMod(hash, size);

            if (!bits.get(index)) {
                return false;
            }
        }

        return true;
    }

    private int hash(String value, int seed) {

        int hash = seed;

        for (char c : value.toCharArray()) {
            hash = 31 * hash + c;
        }

        return hash;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.bloom_filter;

/**
 *
 * @author endovelico
 */
public class BloomFilterMain {


    public static void main(String[] args) {

        BloomFilter bloomFilter =
                new BloomFilter(1000, 3);

        bloomFilter.add("Alice");
        bloomFilter.add("Bob");
        bloomFilter.add("Charlie");

        System.out.println(
                bloomFilter.mightContain("Alice")
        );

        System.out.println(
                bloomFilter.mightContain("Bob")
        );

        System.out.println(
                bloomFilter.mightContain("David")
        );
    }
}
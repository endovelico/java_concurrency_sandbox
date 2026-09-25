/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.bitcask;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Bitcask implements Closeable {

    private final RandomAccessFile file;
    private final Map<String, Long> index = new HashMap<>();

    public Bitcask(String filename) throws IOException {
        file = new RandomAccessFile(filename, "rw");

        // Rebuild the in-memory index from the existing file.
        rebuildIndex();
    }

    public synchronized void put(String key, String value)
            throws IOException {

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

        // Move to end of file.
        long offset = file.length();
        file.seek(offset);

        // Record format:
        //
        // [key length][value length][key][value]
        //
        file.writeInt(keyBytes.length);
        file.writeInt(valueBytes.length);
        file.write(keyBytes);
        file.write(valueBytes);

        // Update in-memory index.
        index.put(key, offset);

        file.getFD().sync();
    }

    public synchronized String get(String key)
            throws IOException {

        Long offset = index.get(key);

        if (offset == null) {
            return null;
        }

        file.seek(offset);

        int keyLength = file.readInt();
        int valueLength = file.readInt();

        byte[] keyBytes = new byte[keyLength];
        byte[] valueBytes = new byte[valueLength];

        file.readFully(keyBytes);
        file.readFully(valueBytes);

        String storedKey =
                new String(keyBytes, StandardCharsets.UTF_8);

        String value =
                new String(valueBytes, StandardCharsets.UTF_8);

        return value;
    }

    private void rebuildIndex() throws IOException {

        long offset = 0;

        while (offset < file.length()) {

            file.seek(offset);

            int keyLength = file.readInt();
            int valueLength = file.readInt();

            byte[] keyBytes = new byte[keyLength];

            file.readFully(keyBytes);

            String key =
                    new String(keyBytes, StandardCharsets.UTF_8);

            // The current record is the latest record
            // for this key.
            index.put(key, offset);

            // Move to next record.
            offset +=
                    Integer.BYTES +
                    Integer.BYTES +
                    keyLength +
                    valueLength;
        }
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
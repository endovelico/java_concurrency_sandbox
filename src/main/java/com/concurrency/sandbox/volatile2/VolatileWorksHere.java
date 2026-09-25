/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.volatile2;

/**
 *
 * @author endovelico
 */
public class VolatileWorksHere {
     /*
     * USE CASE #1
     *
     * A shared flag.
     *
     * Thread A writes false.
     * Thread B needs to see that change.
     */
    private volatile boolean running = true;


    /*
     * USE CASE #2
     *
     * Publishing/replacing an object.
     *
     * The reference is volatile, so another thread
     * that reads it will see the newly published object
     * and the writes that happened before publication.
     */
    private volatile Config config;


    /*
     * USE CASE #3
     *
     * A simple shared state value.
     */
    private volatile int state = 0;


    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }


    public void reloadConfig() {
        // Build the object completely first.
        Config newConfig = new Config(
                "production",
                8080
        );

        // Then publish it.
        config = newConfig;
    }

    public Config getConfig() {
        return config;
    }


    public void setState(int newState) {
        state = newState;
    }

    public int getState() {
        return state;
    }


    public static class Config {

        private final String environment;
        private final int port;

        public Config(String environment, int port) {
            this.environment = environment;
            this.port = port;
        }

        public String getEnvironment() {
            return environment;
        }

        public int getPort() {
            return port;
        }
    }
}

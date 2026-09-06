/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.read_restart;

/**
 *
 * @author endovelico
 */
class Reader implements Runnable {


    private final VersionedData data;


    public Reader(VersionedData data) {
        this.data = data;
    }


    @Override
    public void run() {


        while(true) {


            // 1. Read
            Snapshot snapshot =
                    data.read();


            System.out.println(
                "Reader got value=" +
                snapshot.value +
                " version=" +
                snapshot.version
            );


            // 2. Do long computation
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
            }


            int result =
                snapshot.value * 10;



            // 3. Validate before commit

            if(data.validate(
                    snapshot.version)) {


                System.out.println(
                    "Commit result=" +
                    result
                );

                break;
            }


            else {


                System.out.println(
                    "Conflict detected. Restarting..."
                );
            }
        }
    }
}

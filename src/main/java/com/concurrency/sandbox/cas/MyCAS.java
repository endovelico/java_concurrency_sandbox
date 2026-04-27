package com.concurrency.sandbox.cas;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class MyCAS {

    static Unsafe unsafe;
    static long valueOffset;

    volatile int value = 0;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            valueOffset = unsafe.objectFieldOffset(
                    MyCAS.class.getDeclaredField("value")
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    boolean compareAndSwap(int expected, int update) {
        return unsafe.compareAndSwapInt(
                this,
                valueOffset,
                expected,
                update
        );
    }

}

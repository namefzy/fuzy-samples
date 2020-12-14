package com.fuzy.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * description: unsafe类测试
 *
 * @author: fuzy
 * @create: 2020-12-10 23:00
 * @version:
 **/
public class TestUnsafe {
    static  Unsafe unsafe;

    static long stateOffset;

    private volatile long state = 0;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestUnsafe test = new TestUnsafe();
        boolean b = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);
        System.out.println(b);
    }
}

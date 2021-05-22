package com.fuzy.example.threadlocal;

/**
 * @ClassName ThreadLocalExample
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/26 22:51
 * @Version 1.0.0
 */
public class ThreadLocalExample {

    public static void main(String[] args) {
        InheritableThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("Thread-----HelloWorld");
        Thread thread1 = new Thread(() -> {

            System.out.println(threadLocal.get());
        });

        thread1.start();
        System.out.println(threadLocal.get());

        ThreadLocal threadLocal1 = new ThreadLocal();
        threadLocal1.set("Thread2-----HelloWorld!");
        Thread thread2 = new Thread(() -> {

            System.out.println(threadLocal1.get());
        });

        thread2.start();
        System.out.println(threadLocal1.get());
    }
}

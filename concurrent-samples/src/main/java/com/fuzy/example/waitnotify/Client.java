package com.fuzy.example.waitnotify;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 22:52
 * @Version 1.0.0
 */
public class Client {


    public static void main(String[] args) {
        User user = new User(10);
        Thread thread2 = new Thread(new NotifyRunnable(user));
        Thread thread1 = new Thread(new WaitRunnable(user));

        thread1.start();
        thread2.start();
    }
}

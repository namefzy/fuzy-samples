package com.fuzy.example.daemon;

/**
 * @ClassName DaemonDemo
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/26 22:36
 * @Version 1.0.0
 */
public class DaemonDemo {
    public static void main(String[] args) {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}

package com.fuzy.example.interrupt;

/**
 * @ClassName InterruptDemo
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/25 22:52
 * @Version 1.0.0
 */
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){

                }
                //复位 false
                System.out.println("threadOne isInterrupted:"+Thread.currentThread().isInterrupted());

                while (!Thread.interrupted()){
                    System.out.println(11111);
                }
            }
        });

        threadA.start();

        //获取中断标志 false
        System.out.println("isInterrupted:"+threadA.isInterrupted());
        //设置中断标志位为true,推出for循环
        threadA.interrupt();

        Thread.sleep(2000);
        //获取中断标志 false
        System.out.println("isInterrupted:"+threadA.isInterrupted());
        System.out.println();

        threadA.join();

        System.out.println("main thread is over");

    }
}

package com.fuzy.example.futuredemo;

/**
 * @ClassName FutureBootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/18 23:07
 * @Version 1.0.0
 */
public class FutureBootstrap {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        FutureData<String> futureData    = server.getString();

        //执行其他任务
        String hello = "hello";
        Thread.sleep(1000);
        System.out.print(hello + " " + futureData.getData());
    }
}

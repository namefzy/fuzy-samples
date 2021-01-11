package com.fuzy.example.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin");
        //使线程获取到许可证
//        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("end");
    }
}

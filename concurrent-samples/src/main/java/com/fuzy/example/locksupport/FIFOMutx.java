package com.fuzy.example.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutx {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<>();
    public void lock(){
        boolean wasInterrupted = false;
        Thread currentThread = Thread.currentThread();
        waiters.add(currentThread);
        //如果当前线程不是队首或者当前锁已经被其他线程获取，则挂起
        while (waiters.peek()!=currentThread||!locked.compareAndSet(false,true)){
            LockSupport.park(this);
            //如果当前线程被终端，则重置标记，重新循环
            if(Thread.interrupted()){
                wasInterrupted = true;
            }
        }
        waiters.remove();
        //如果判断标记为true则中断该线程
        if(wasInterrupted){
            currentThread.interrupt();
        }
    }
    public void unlock(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}

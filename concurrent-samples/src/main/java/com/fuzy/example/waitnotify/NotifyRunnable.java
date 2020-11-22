package com.fuzy.example.waitnotify;

/**
 * @ClassName NotifyRunable
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 22:56
 * @Version 1.0.0
 */
public class NotifyRunnable implements Runnable{

    private User user;

    public NotifyRunnable(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        synchronized (user){
            System.out.println("notify唤醒前");
            user.notify();
            System.out.println("notify唤醒后");
        }

    }
}

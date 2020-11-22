package com.fuzy.example.waitnotify;

/**
 * @ClassName WaitRunnable
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 22:51
 * @Version 1.0.0
 */
public class WaitRunnable implements Runnable{

    private User user;

    public WaitRunnable(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        synchronized (user){
            try {
                System.out.println("wait等待前");
                user.wait();
                System.out.println("wait等待后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

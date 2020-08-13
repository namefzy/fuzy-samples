package com.example.fuzy.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName Bootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/10 21:24
 * @Version 1.0.0
 */
public class Bootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigApplicationListener.class);
        context.publishEvent(new MyApplicationEvent("我的事件"));
        context.close();
    }
}

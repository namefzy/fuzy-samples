package com.example.fuzy.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

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
        Collection<ApplicationListener<?>> applicationListeners =
                context.getApplicationListeners();
        applicationListeners.forEach(System.err::println);
        context.publishEvent(new MyApplicationEvent("我的事件"));
        context.close();
    }
}

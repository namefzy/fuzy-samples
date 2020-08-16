package com.example.fuzy.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyAnnotationListener
 * @Description 注解方式实现的监听器
 * @Author 11564
 * @Date 2020/8/13 22:58
 * @Version 1.0.0
 */
@Component
public class MyAnnotationListener {
//    @EventListener
    public void register(ApplicationEvent applicationEvent){
        System.out.println("注解方式实现监听器："+applicationEvent);
    }
}

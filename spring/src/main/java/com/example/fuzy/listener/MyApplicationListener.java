package com.example.fuzy.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyApplicationListener
 * @Description 自定义事件监听
 * @Author 11564
 * @Date 2020/8/10 21:23
 * @Version 1.0.0
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("正常方式实现："+event);
    }
}

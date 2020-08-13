package com.example.fuzy.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName MyApplicationEvent
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/10 21:24
 * @Version 1.0.0
 */
public class MyApplicationEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MyApplicationEvent(Object source) {
        super(source);
    }
}

package com.fuzy.example.springboot.initializer;

import com.fuzy.example.springboot.controller.DemoController;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName MyApplicationInitializer
 * @Description TODO
 * @Author 11564
 * @Date 2020/9/6 20:48
 * @Version 1.0.0
 */
public class MyApplicationInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("MyApplicationInitializer");
    }
}

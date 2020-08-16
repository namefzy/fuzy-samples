package com.fuzy.example;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * description: 非web启动方式
 * @author: fuzy
 * @create: 2020-07-17 23:45
 * @version: 1.0.0
 **/
@DubboComponentScan
@SpringBootApplication
public class DubboClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DubboClientApplication.class)
                .run(args);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.err.println(beanDefinitionNames[i]);
        }
    }
}

package com.fuzy.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * description: 非web启动方式
 *
 * @author: fuzy
 * @create: 2020-07-17 23:45
 * @version: 1.0.0
 **/
@EnableAutoConfiguration
public class DubboClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

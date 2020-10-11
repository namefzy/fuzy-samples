package com.fuzy.example.springboot;

import com.fuzy.example.springboot.controller.DemoController;
import com.fuzy.example.springboot.initializer.MyApplicationInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws ClassNotFoundException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class);
        DemoController bean = run.getBean(DemoController.class);
        bean.demo();
    }

}

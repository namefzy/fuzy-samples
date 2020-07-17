package com.fuzy.example;

import com.fuzy.example.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: dubbo消费者
 *
 * @author: fuzy
 * @create: 2020-07-18 00:02
 * @version: 1.0.0
 **/
@SpringBootApplication
@RestController
public class DubboConsumerApplication {

    @Reference(url = "dubbo://192.168.199.218:20880/com.fuzy.example.service.OrderService")
    private OrderService orderService;

    @Bean
    public ApplicationRunner runner(){
        return args -> System.out.println(orderService.getOrderByOrderCode("M12343434"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class,args);
    }
}

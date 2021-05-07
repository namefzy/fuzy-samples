package com.fuzy.example;

import com.fuzy.example.order.client.OrderServiceFeignClient;
import com.fuzy.example.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableFeignClients//启动openFeign
public class SpringCloudOpenfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOpenfeignApplication.class, args);
    }

    @Autowired
    private OrderServiceFeignClient orderServiceFeignClient;

    @GetMapping("/order/query")
    public Order query(){
        Order order = orderServiceFeignClient.queryOrderById();
        return order;
    }
}

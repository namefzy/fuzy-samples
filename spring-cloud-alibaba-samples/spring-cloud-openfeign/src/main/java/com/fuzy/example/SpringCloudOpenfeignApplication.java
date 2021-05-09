package com.fuzy.example;

import com.fuzy.example.order.client.OrderServiceFeignClient;
import com.fuzy.example.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
@EnableFeignClients//启动openFeign
public class SpringCloudOpenfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudOpenfeignApplication.class, args);
    }

    @Autowired
    private OrderServiceFeignClient orderServiceFeignClient;

    @GetMapping("/order/query/list")
    public void list(){
        List<Order> list = orderServiceFeignClient.list();
        System.out.println(list);
    }

    @GetMapping("/order/query/{id}")
    public Order query(@PathVariable("id")int id){
        Order order = orderServiceFeignClient.queryOrderById(id);
        return order;
    }

    @GetMapping("/order/query/details")
    public void orderDetails(){
        orderServiceFeignClient.testLanguage("傅某");
    }

    @PostMapping("/order/add")
    public void add(){
        Order order = new Order();
        order.setId(2);
        order.setRemark("post请求");
        orderServiceFeignClient.add(order);
    }
}

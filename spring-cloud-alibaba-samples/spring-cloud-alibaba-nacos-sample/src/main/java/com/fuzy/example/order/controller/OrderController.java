package com.fuzy.example.order.controller;

import com.fuzy.example.order.domain.Order;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/7 16:50
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @GetMapping("/query/list")
    public List<Order> list(){
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setId(1);
        order.setOrderCode("1234");

        Order order2 = new Order();
        order2.setCreateDate(new Date());
        order2.setId(2);
        order2.setOrderCode("1234");
        List<Order> list = new ArrayList<>();
        list.add(order);
        list.add(order2);
        return list;
    }

    @GetMapping("/query/single")
    public Order queryOrderById(@RequestParam("id") int id){
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setId(id);
        order.setOrderCode("1234");
        return order;
    }

    @PostMapping("/add")
    public void addOrder(@RequestBody Order order){
        System.out.println(order);
    }

    @GetMapping("/details")
    public void orderDetails(@RequestParam("name")String name){
        System.out.println(name);
    }
}

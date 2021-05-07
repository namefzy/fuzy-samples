package com.fuzy.example.order.controller;

import com.fuzy.example.order.domain.Order;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/7 16:50
 */
@RestController("/order")
public class OrderController {

    @GetMapping("/query/{id}")
    public Order queryOrderById(@PathVariable(value = "id")String id){
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setId(1);
        order.setOrderCode("1234");
        return order;
    }

    @PostMapping("/add")
    public void addOrder(Order order){

    }
}

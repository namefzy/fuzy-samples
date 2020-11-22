package com.fuzy.example.visitor;

import java.util.*;

/**
 * @ClassName Customer
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:35
 * @Version 1.0.0
 */
public class Customer implements Element{
    private String name;
    private List<Order> orders = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void addOrder(Order order) {
        orders.add(order);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Order order : orders) {
            order.accept(visitor);
        }
    }
}

package com.fuzy.example.order.service.impl;

import com.fuzy.example.order.domain.Order;
import com.fuzy.example.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author fzy
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder(Order order) {
        System.out.println(order);
    }
}

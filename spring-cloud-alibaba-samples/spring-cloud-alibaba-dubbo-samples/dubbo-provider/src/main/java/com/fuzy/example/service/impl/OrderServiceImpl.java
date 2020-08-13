package com.fuzy.example.service.impl;

import com.fuzy.example.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * description: 订单实现类
 *
 * @author: fuzy
 * @create: 2020-07-17 23:11
 * @version: 1.0.0
 **/

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${server.port}")
    private int port;
    @Override
    public String getOrderByOrderCode(String orderCode) {

        return String.format("OrderCode=%s;port=%s",orderCode,port);
    }
}

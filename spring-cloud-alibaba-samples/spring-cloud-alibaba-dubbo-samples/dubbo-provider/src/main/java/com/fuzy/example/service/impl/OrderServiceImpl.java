package com.fuzy.example.service.impl;

import com.fuzy.example.service.OrderService;
import org.apache.dubbo.config.annotation.Service;

/**
 * description: 订单实现类
 *
 * @author: fuzy
 * @create: 2020-07-17 23:11
 * @version: 1.0.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String getOrderByOrderCode(String orderCode) {
        return String.format("传过来的OrderCode是%s",orderCode);
    }
}

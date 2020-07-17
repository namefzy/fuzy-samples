package com.fuzy.example.service;

/**
 * description: dubbo服务调用api
 *
 * @author: fuzy
 * @create: 2020-07-17 23:06
 * @version: 1.0.0
 **/
public interface OrderService {
    /**
    *@Description
    *@Author fuzy
    *@Date 2020/7/17 23:07
    *@Param ordercode 订单编号
    *@Return
    */
    String getOrderByOrderCode(String orderCode);
}

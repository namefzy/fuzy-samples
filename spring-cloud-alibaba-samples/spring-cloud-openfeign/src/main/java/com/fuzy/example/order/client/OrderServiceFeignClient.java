package com.fuzy.example.order.client;

import com.fuzy.example.order.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author fuzy
 */
@FeignClient(name = "order-service")
public interface OrderServiceFeignClient {

    @GetMapping("/order/query")
    Order queryOrderById();
}

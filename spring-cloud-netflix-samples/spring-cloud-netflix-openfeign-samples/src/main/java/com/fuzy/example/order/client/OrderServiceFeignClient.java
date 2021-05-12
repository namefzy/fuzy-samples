package com.fuzy.example.order.client;

import com.fuzy.example.order.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * FeignClient中的order-service对应着注册到服务中心的服务名
 * @author fuzy
 */
@FeignClient(name = "order-service")
public interface OrderServiceFeignClient {

    /**
     * 无参接口调用
     */
    @GetMapping("/order/query/list")
    List<Order> list();

    /**
     * 有参接口调用
     * @param id
     * @return
     */
    @GetMapping("/order/query/single")
    Order queryOrderById(@RequestParam("id") int id);

    /**
     * json数据
     * @param order
     */
    @PostMapping("/order/add")
    void add(@RequestBody Order order);

    /**
     * 测试中文乱码
     * @param name
     */
    @GetMapping("/order/details")
    void testLanguage(@RequestParam("name") String name);
}

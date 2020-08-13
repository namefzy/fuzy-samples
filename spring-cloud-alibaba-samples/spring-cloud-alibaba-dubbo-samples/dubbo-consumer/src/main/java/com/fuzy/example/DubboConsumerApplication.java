package com.fuzy.example;

import com.fuzy.example.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: dubbo消费者
 *
 * @author: fuzy
 * @create: 2020-07-18 00:02
 * @version: 1.0.0
 **/
@SpringBootApplication
@RestController
public class DubboConsumerApplication {

    /**
     * url:用于直接调用的服务目标URL（如果已指定），则注册表中心无效。
     * check:在启动过程中检查服务提供商是否可用，默认值为true.
     * loadbalance:负载均衡
     *      random:随机算法（默认）
     *      roundrobin:轮询
     *      leastActive:最小活跃调用数
     * cluster:容错模式
     *      failover:失败自动切换节点重试，默认会重试两次（retries=2）,重试会导致更长的响应，并且可能会导致事务性操作带来重复数据
     *      failfast:快速失败。当服务调用失败，立即报错
     *      failsafe:失败安全。出现异常时，直接忽略
     *      failback:失败后自动回复。服务调用出现异常，在后台记录这条失败的请求定时重发
     *      forking:并行调用集群中的多个服务，只要其中一个成功就返回，可以通过forks设置最大并行数
     *      broadcast:广播调用所有的服务提供者，任意一个服务报错则表示服务调用失败
     * mock:服务降级；当服务提供方出现网络异常无法访问时，客户端不抛出异常，而是通过降级配置返回兜底数据。
     *      例如：远程服务挂了，此时通过服务降级返回友好提示数据
     */
    @Reference
    private OrderService orderService;

    @Bean
    public ApplicationRunner runner(){
        return args -> System.out.println(orderService.getOrderByOrderCode("M12343434"));
    }

    @GetMapping("/user")
    public String getUserWithOrder(){
        String orderCode = "M1234";
        return orderService.getOrderByOrderCode(orderCode);
    }
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class,args);
    }
}

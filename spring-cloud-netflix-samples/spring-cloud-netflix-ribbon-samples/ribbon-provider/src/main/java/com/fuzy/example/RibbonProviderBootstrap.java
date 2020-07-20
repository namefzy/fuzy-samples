package com.fuzy.example;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2020/7/20 20:41
 */
@SpringBootApplication
@RestController
@RequestMapping("/provider")
public class RibbonProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(RibbonProviderBootstrap.class,args);
    }

    @GetMapping("/order")
    public String queryOrder(@RequestParam String id,@RequestParam String orderName){
        return String.format("id=%s,订单名称:%s",id,orderName);
    }
}

package com.fuzy.example.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description 测试配置中心
 * @Author fuzy
 * @Date 2021/5/13 20:56
 * @Version 1.0
 */
@RestController
public class TestController {

    @Value("${custom.flag}")
    private String flag;

    @Value("${custom.database}")
    private String database;

    @GetMapping("/test")
    public String test(){
        return String.format("flag: %s；</br> database: %s",flag,database);
    }
}

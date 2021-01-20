package com.example.fuzy.redis.controller;

import com.example.fuzy.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisTestController
 * @Description redis测试类
 * @Author fuzy
 * @Date 2021/1/16 16:28
 * @Version 1.0
 */
@RestController
public class RedisTestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/getSet")
    public void set(){
        redisUtils.set("1","1");
        System.out.println(redisUtils.get("1"));
    }
}

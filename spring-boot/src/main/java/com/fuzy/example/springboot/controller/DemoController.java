package com.fuzy.example.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author 11564
 * @Date 2020/9/6 20:47
 * @Version 1.0.0
 */
@RestController
public class DemoController {

    @GetMapping("demo")
    public void demo(){
        System.out.println("111111");
    }
}

package com.fuzy.example;

import com.fuzy.example.service.Driver;

import java.util.ServiceLoader;

/**
 * @ClassName Bootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/7/18 16:12
 * @Version 1.0.0
 */
public class Bootstrap {
    public static void main(String[] args) {
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        serviceLoader.forEach(driver -> System.out.println(driver.connect()));
    }
}

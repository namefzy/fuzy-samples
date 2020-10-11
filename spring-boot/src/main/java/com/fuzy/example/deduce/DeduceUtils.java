package com.fuzy.example.deduce;

import org.springframework.boot.WebApplicationType;
import org.springframework.util.ClassUtils;

/**
 * @ClassName DeduceUtils
 * @Description 根据jar包类型推测web类型
 * @Author 11564
 * @Date 2020/9/9 22:04
 * @Version 1.0.0
 */
public class DeduceUtils {
    public static void main(String[] args) {
        String name = DeduceUtils.class.getName();
        System.out.println(name);
    }
}

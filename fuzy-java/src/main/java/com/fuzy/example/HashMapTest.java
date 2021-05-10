package com.fuzy.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HashMapTest
 * @Description TODO
 * @Author fuzy
 * @Date 2021/5/7 22:56
 * @Version 1.0
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        String put = map.put("1", "1");
        System.out.println(put);
        String put1 = map.put("2", "2");
        System.out.println(put1);
        String put2 = map.put("2", "3");
        System.out.println(put2);

    }
}

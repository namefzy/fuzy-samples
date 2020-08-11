package com.fuzy.example.demo;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2020/8/11 11:27
 */
public class Son extends Parent{

    private String name;

    private int age;

    public static void main(String[] args) {
        Son son = new Son();
        son.setAge(1);
        son.setName("小王");
        son.run();
    }
}

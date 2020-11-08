package com.fuzy.example.builder.model2;

/**
 * @ClassName Test
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:28
 * @Version 1.0.0
 */
public class Test {
    public static void main(String[] args) {
        ConcreteBuilder concreteBuilder = new ConcreteBuilder();
        Product build = concreteBuilder
                .bulidA("牛肉煲")
//              .bulidC("全家桶")
                .bulidD("冰淇淋")
                .build();
        System.out.println(build.toString());
    }
}

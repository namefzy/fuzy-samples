package com.fuzy.example.builder.model1;

/**
 * @ClassName Test
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/8 11:09
 * @Version 1.0.0
 */
public class Test {
    public static void main(String[] args) {
        Director director = new Director();
        Product create = director.create(new ConcreteBuilder());
        System.out.println(create.toString());
    }
}

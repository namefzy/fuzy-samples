package com.fuzy.example.demo;

/**
 * @ClassName Bootstrap
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/2 16:11
 * @Version 1.0.0
 */
public class Bootstrap {
    public static void main(String[] args) {
        Animal animal = new Animal() {
            @Override
            public void eat() {
                System.out.println("狗吃饭");
            }
        };
        animal.eat();
        animal.drink();
    }
}

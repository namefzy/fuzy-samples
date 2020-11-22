package com.fuzy.example.facade;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 8:49
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        ElephantStepFacade elephantStepFacade = new ElephantStepFacade();
        elephantStepFacade.operation();
    }
}

package com.fuzy.example.facade;

/**
 * @ClassName ElephantStepFacade
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 8:48
 * @Version 1.0.0
 */
public class ElephantStepFacade {

    Step step = new ElephantStep();

    public void operation(){
        step.openRefrigerator();
        step.putElephant();
        step.closeRefrigerator();
    }
}

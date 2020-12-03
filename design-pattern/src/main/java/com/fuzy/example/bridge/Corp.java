package com.fuzy.example.bridge;

/**
 * @ClassName Corp
 * @Description TODO
 * @Author 11564
 * @Date 2020/12/3 7:26
 * @Version 1.0.0
 */
public abstract class Corp {
    private Product product;

    public Corp(Product product){
        this.product = product;
    }

    public void makeMoney(){
        this.product.beProducted();
        this.product.beSelled();
    }
}

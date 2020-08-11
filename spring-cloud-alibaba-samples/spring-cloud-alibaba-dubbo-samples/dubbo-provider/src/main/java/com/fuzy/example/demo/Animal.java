package com.fuzy.example.demo;

import javax.annotation.PostConstruct;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2020/8/10 14:35
 */
public abstract class Animal {

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getName());
    }
}

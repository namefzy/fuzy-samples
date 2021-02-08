package com.example.fuzy.spring.ioc.dependency.lookup.domain;

import com.example.fuzy.spring.ioc.dependency.lookup.annotation.Super;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/2/7 10:54
 */
@Super
public class Son extends Parent{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Son{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}

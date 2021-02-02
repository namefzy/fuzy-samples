package com.fuzy.spring.model;

import com.fuzy.spring.annotation.Super;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/2/2 17:49
 */
@Super
public class SonUser extends User{

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SonUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}

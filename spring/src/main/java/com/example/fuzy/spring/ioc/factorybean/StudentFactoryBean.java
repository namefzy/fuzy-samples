package com.example.fuzy.spring.ioc.factorybean;

import com.example.fuzy.spring.model.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName StudentFactoryBean
 * @Description TODO
 * @Author fuzy
 * @Date 2021/2/3 22:51
 * @Version 1.0
 */
public class StudentFactoryBean implements FactoryBean<User> {

    private User user;

    @Override
    public User getObject() throws Exception {
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

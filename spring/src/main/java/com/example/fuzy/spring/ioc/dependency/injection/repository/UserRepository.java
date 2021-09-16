package com.example.fuzy.spring.ioc.dependency.injection.repository;

import com.example.fuzy.spring.ioc.dependency.lookup.domain.Parent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/7 17:47
 */
public class UserRepository {

    private Collection<Parent> users; // 自定义 Bean

    private BeanFactory beanFactory; // 內建非 Bean 对象（依赖）

    private ObjectFactory<ApplicationContext> objectFactory;

    public Collection<Parent> getUsers() {
        return users;
    }

    public void setUsers(Collection<Parent> users) {
        this.users = users;
    }


    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }
}

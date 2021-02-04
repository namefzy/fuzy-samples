package com.example.fuzy.spring.bean.instantiation;

import com.example.fuzy.spring.model.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName BeanInstantiationDemo
 * @Description 实例化bean
 * @Author fuzy
 * @Date 2021/2/4 22:34
 * @Version 1.0
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF\\bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-by-static-method", User.class);
        System.out.println("静态方法创建的bean:"+user);
        User user1 = beanFactory.getBean("user-by-instance-method", User.class);
        System.out.println("实例Bean方法实例化Bean:"+user1);
        System.out.println("静态方法Bean是否等于实例创建Bean:"+(user==user1));

        User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println("FactoryBean实例化Bean:"+userByFactoryBean);
        System.out.println("实例Bean方法实例化Bean与FactoryBean实例化Bean:"+(user1==userByFactoryBean));
        System.out.println("静态方法Bean与FactoryBean实例化Bean:"+(user==userByFactoryBean));

    }
}

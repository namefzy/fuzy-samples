package com.example.fuzy.spring.ioc.factorybean;

import com.example.fuzy.spring.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName FactoryBeanDemo
 * @Description
 * @Author fuzy
 * @Date 2021/2/3 22:49
 * @Version 1.0
 */
public class FactoryBeanDemo {

    /**
    * @Author fuzy
    * @Description 测试BeanFactory 与factoryBean 以及普通bean对象
    * @Date 2021/2/3 22:50
    */
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF\\FactoryBean.xml");
        User student = (User) applicationContext.getBean("student");
        System.out.println(student);
        StudentFactoryBean bean = (StudentFactoryBean) applicationContext.getBean("&student");
        System.out.println(bean);
        User object = bean.getObject();
        System.out.println(student==object);
    }
}

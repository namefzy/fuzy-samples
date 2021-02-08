package com.example.fuzy.spring.ioc.dependency.lookup.factorybean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/2/7 18:00
 */
public class UserFactoryBeanDemo {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF\\student-factory-bean.xml");
        Student student = (Student) applicationContext.getBean("studentFactoryBean");
        System.out.println("普通方式获取Bean:"+student);
        StudentFactoryBean bean = (StudentFactoryBean) applicationContext.getBean("&studentFactoryBean");
        Student object = bean.getObject();
        System.out.println("通过factoryBean获取"+object);
        System.out.println(student==object);
    }
}

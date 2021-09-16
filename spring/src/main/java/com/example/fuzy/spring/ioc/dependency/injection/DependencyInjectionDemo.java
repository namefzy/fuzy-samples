package com.example.fuzy.spring.ioc.dependency.injection;

import com.example.fuzy.spring.ioc.dependency.injection.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/7 17:42
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF\\injection\\dependency-injection-context.xml");
        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getUsers());


        // 依赖来源二：依赖注入（內建依赖）
        BeanFactory beanFactory = userRepository.getBeanFactory();
        System.out.println("依赖注入的bean:"+beanFactory);
        System.out.println(applicationContext==beanFactory);

        ObjectFactory userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory);
        System.out.println(userFactory == beanFactory);

        //容器内建bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);
    }
}

package com.fuzy.annotation.group;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/5/12 10:43
 */
@Group
public class AnnotationDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        if(AnnotationDemo.class.isAnnotationPresent(Group.class)){
            applicationContext.register(AnnotationDemo.class);
        }
        applicationContext.refresh();

        AnnotationDemo annotationDemo = applicationContext.getBean(AnnotationDemo.class);
        annotationDemo.sayHello();

        applicationContext.close();
    }

    public void sayHello(){
        System.out.println("Hello World!");
    }
}

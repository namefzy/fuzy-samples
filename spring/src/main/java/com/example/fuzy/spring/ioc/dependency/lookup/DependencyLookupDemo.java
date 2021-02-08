package com.example.fuzy.spring.ioc.dependency.lookup;

import com.example.fuzy.spring.ioc.dependency.lookup.annotation.Super;
import com.example.fuzy.spring.ioc.dependency.lookup.domain.Parent;
import com.example.fuzy.spring.ioc.dependency.lookup.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author fuzy
 * @version 1.0
 * @Description 依赖查找
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2021/2/7 10:23
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF\\lookup\\dependency-lookup-context.xml");
        //单一类型查找
        lookupByName(applicationContext);
        lookupByType(applicationContext);
        lookupByBeanProvider(applicationContext);

        //集合类型查找- ListableBeanFactory
        lookupCollectionByType(applicationContext);
        lookupCollectionByAnnotation(applicationContext);

        //层次性依赖查找
        lookupByHierarchical();
    }

    private static void lookupByHierarchical() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(DependencyLookupDemo.class);

        // 1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        // 2. 设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        displayContainsLocalBean(beanFactory, "user");
        displayContainsLocalBean(parentBeanFactory, "user");

        displayContainsBean(beanFactory, "user");
        displayContainsBean(parentBeanFactory, "user");

        // 启动应用上下文
        applicationContext.refresh();

        // 关闭应用上下文
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 Bean[name : %s] : %s\n", beanFactory, beanName,
                containsBean(beanFactory, beanName));
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local Bean[name : %s] : %s\n", beanFactory, beanName,
                beanFactory.containsLocalBean(beanName));
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath 路径
        String location = "classpath:/META-INF/lookup/dependency-lookup-context.xml";
        // 加载配置
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }

    @Bean
    public String helloWorld(){
        return "Hello,World";
    }

    @Bean
    public User user(){
        User user = new User();
        user.setId(1);
        user.setName("fuzy");
        return user;
    }

    /**
     * 集合根据注解查找
     * @param applicationContext
     */
    private static void lookupCollectionByAnnotation(ApplicationContext applicationContext) {
        if(applicationContext instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = applicationContext;
            Map<String, Parent> parentMap = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 所有的 Parent 集合对象：" + parentMap);
        }
    }

    /**
     * 集合根据类型查找
     * @param applicationContext
     */
    private static void lookupCollectionByType(ApplicationContext applicationContext) {
        if(applicationContext instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = applicationContext;
            Map<String, Parent> map = listableBeanFactory.getBeansOfType(Parent.class);
            System.out.println("集合根据类型查找："+map);
        }
    }

    /**
     * 单一类型-根据BeanProvider获取bean
     * @param applicationContext
     */
    private static void lookupByBeanProvider(ApplicationContext applicationContext) {
        ObjectProvider<Parent> beanProvider = applicationContext.getBeanProvider(Parent.class);
        System.out.println("单一类型-根据beanProvider查找："+beanProvider.getObject());
    }

    /**
     * 单一类型-根据类型查找
     * @param applicationContext
     */
    private static void lookupByType(ApplicationContext applicationContext) {
        Parent parent = applicationContext.getBean(Parent.class);
        System.out.println("单一类型-根据类型查找："+ parent);
    }

    /**
     * 单一类型-根据名称查找
     */
    private static void lookupByName(ApplicationContext applicationContext) {
        Parent parent = (Parent) applicationContext.getBean("parent");
        System.out.println("单一类型-根据名称查找："+ parent);
    }
}

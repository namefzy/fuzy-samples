# Spring

## `IoC`

### **前景概要**

**类图**

![AbstractApplicationContext](https://image-1301573777.cos.ap-chengdu.myqcloud.com/AbstractApplicationContext.png)

​																		        **`AbstractApplictionContext`**

`ListableBeanFactory`	

> bean工厂将实现`BeanFactory`接口的扩展，这些bean工厂可以枚举它们的所有bean实例，而不是按照客户的请求逐个尝试bean查找。具体实现类`DefaultListableBeanFactory`。

`HierarchicalBeanFactory`

>  提供父容器的访问功能，至于父容器的设置，需要找`ConfigurableBeanFactory`的`setParentBeanFactory`

`ConfigurableBeanFactory`

### 依赖查找

#### 代码示例

**配置文件**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans
        xmlns="http://www.springframework.org/schema/beans"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
	<!--User类-->
    <bean id="user" class="org.geekbang.thinking.in.spring.ioc.overview.domain.User">
        <property name="id" value="1"/>
        <property name="name" value="小马哥"/>
        <property name="city" value="HANGZHOU"/>
        <property name="workCities" value="BEIJING,HANGZHOU"/>
        <property name="lifeCities">
            <list>
                <value>BEIJING</value>
                <value>SHANGHAI</value>
            </list>
        </property>
        <property name="configFileLocation" value="classpath:/META-INF/user-config.properties"/>
    </bean>
	<!--SuperUser继承User并新增address属性-->
    <bean id="superUser" class="org.geekbang.thinking.in.spring.ioc.overview.domain.SuperUser" parent="user"
          primary="true">
        <property name="address" value="杭州"/>
    </bean>

    <!--实现延迟查找，注入User对象-->
    <bean id="objectFactory" class="org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean">
        <property name="targetBeanName" value="user"/>
    </bean>

</beans>
```

**启动类**

```java
public static void main(String[] args) {
    // 配置 XML 配置文件
    // 启动 Spring 应用上下文
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
    // 按照类型查找
    lookupByType(beanFactory);
    // 按照类型查找结合对象
    lookupCollectionByType(beanFactory);
    // 通过注解查找对象
    lookupByAnnotationType(beanFactory);
	//根据名称实时查找
    lookupInRealTime(beanFactory);
    //根据名称延迟查找
    lookupInLazy(beanFactory);
    //根据类型和名称查找
    lookupByNameAndType(beanFactory);
}
```

- 根据Bean名称查找

  - 实时查找

    ```java
    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找：" + user);
    }
    ```

  - 延迟查找

    ```java
    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找：" + user);
    }
    ```

- 根据Bean类型查找

  - 单个Bean对象

    ```java
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找：" + user);
    }
    ```

  - 集合Bean对象

    ```java
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有的 User 集合对象：" + users);
        }
    }
    ```

- 根据Bean名称查找+类型查找

  ```java
  private static void lookupByNameAndType(BeanFactory beanFactory) {
      User user = beanFactory.getBean("user", User.class);
      System.out.println("根据名称和类型查找User对象： "+user);
  }
  ```

- 根据Java注解查找

  **备注：@Super注解标记了`SuperUser`类**

  - 单个Bean对象

  - 集合Bean对象

    ```java
    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 所有的 User 集合对象：" + users);
        }
    }
    ```

### 依赖注入

#### 构造器注入

```java
public class AnnotationDependencyConstructorInjectionDemo {

    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);


        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找并且创建 Bean
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
	
    //userHolder
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    @Bean
    public User user(){
        return new User();
    }
}

```

#### 字段注入

```java
@Autowired
private UserHolder userHolder;
```

#### 方法注入

```java
public class AnnotationDependencyMethodInjectionDemo {

    private UserHolder userHolder;

    private UserHolder userHolder2;

    @Autowired
    public void init1(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Resource
    public void init2(UserHolder userHolder2) {
        this.userHolder2 = userHolder2;
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    @Bean
    public User user(){
        return new User();
    }


    public static void main(String[] args) {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类） -> Spring Bean
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);


        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 AnnotationDependencyFieldInjectionDemo Bean
        AnnotationDependencyMethodInjectionDemo demo = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);

        // @Autowired 字段关联
        UserHolder userHolder = demo.userHolder;
        System.out.println(userHolder);
        System.out.println(demo.userHolder2);
		//注入的对象依然默认是单例的
        System.out.println(userHolder == demo.userHolder2);


        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

}

```

#### 循环依赖

##### 什么是循环依赖

> 什么是循环依赖？即对象A拥有属性B，对象B拥有属性A，在IoC容器进行依赖注入时就会发生A依赖于，同时B依赖A的情形。

循环依赖问题在Spring中主要有三种情况：

- 通过构造方法进行依赖注入时产生的循环依赖问题。
- 通过setter方法进行依赖注入且是在多例（原型）模式下产生的循环依赖问题。
- 通过setter方法进行依赖注入且是在单例模式下产生的循环依赖问题。

> 注意：在Spring中，只有【第三种方式】的循环依赖问题被解决了，其他两种方式在遇到循环依赖问题时都会产生异常。

其实也很好解释:

- 第一种构造方法注入的情况下，在new对象的时候就会堵塞住了，其实也就是”先有鸡还是先有蛋“的历史难题。
- 第二种setter方法&&多例的情况下，每一次`getBean()`时，都会产生一个新的Bean，如此反复下去就会有无穷无尽的Bean产生了，最终就会导致OOM问题的出现。

##### Spring如何解决循环依赖

![spring循环依赖](https://image-1301573777.cos.ap-chengdu.myqcloud.com/spring循环依赖.png)

![spring循环依赖uml图](https://image-1301573777.cos.ap-chengdu.myqcloud.com/spring循环依赖uml图.jpg)

**三大缓存对象（key-value）**

- `一级缓存-singletonObjects`

  > - 用于存储已经初始化完成的Bean实例
  > - 该缓存是对外使用的，指的就是使用Spring框架的程序员。

- `二级缓存-earlySingletonObjects`

  > - 用于存储单例模式下创建的Bean实例（该Bean被提前暴露的引用，Bean正在创建中）
  > - 该缓存对内使用，spring框架内部使用该缓存
  > - 为了解决第一个`A`的引用最终如何替换为代理对象的问题

- `三级缓存-singletonFactories`

  > 通过`ObjectFactory`对象来存储单例模式下提前暴露的Bean实例的引用（正在创建中）。另外通过前面Spring的依赖查找示例我们可以知道`ObjectFactory`是延迟查找。**也就是在实例化对象后，注入属性前将Bean添加到该缓存中。**

**Spring这三个缓存，用于存储单例的Bean实例，这三个缓存是彼此互斥的，不会针对同一个Bean的实例同时存储。**

> **如果调用`getBean`，则需要从三个缓存中依次获取指定的Bean实例。读取顺序依次是一级缓存-->二级缓存-->三级缓存**

**Spring解决循环依赖核心源码**

```java
protected Object getSingleton(String beanName, boolean allowEarlyReference) {
    //从一级缓存中获取单例对象
    Object singletonObject = this.singletonObjects.get(beanName);
    //singletonObject：如果一级缓存中是否存在
    //isSingletonCurrentlyInCreation：判断当前单例Bean是否正在创建中，也就是没初始化完成(比如A依赖了B对象，在初始化A的过程中先去创建B对象，这时的A就是处于创建中的状态)
    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
        synchronized (this.singletonObjects) {
            //从二级缓存中获取单例Bean
            singletonObject = this.earlySingletonObjects.get(beanName);
            //allowEarlyReference:是否允许从三级缓存singletonFactories拿到对象
            if (singletonObject == null && allowEarlyReference) {
                //从三级缓存中获取单例Bean
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    //从三级缓存移动到二级缓存
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    this.singletonFactories.remove(beanName);
                }
            }
        }
    }
    return singletonObject;
}
```

参考文章1：https://blog.csdn.net/xmtblog/article/details/110102045

参考文章2：https://zhuanlan.zhihu.com/p/84267654

# `ApplicationListener和ApplicationEvent`

## 1、 `Quick Start`

**自定义事件**

```java
public class MyApplicationEvent extends ApplicationEvent {
    
    public MyApplicationEvent(Object source) {
        super(source);
    }
}
```

**自定义监听器**

```java
/**
 * 普通方式
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("正常方式实现："+event);
    }
}

/**
 * 注解实现方式
 */
@Component
public class MyAnnotationListener {
    @EventListener
    public void register(ApplicationEvent applicationEvent){
        System.out.println("注解方式实现监听器："+applicationEvent);
    }
}
```

**测试类**

```java
public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigApplicationListener.class);
    context.publishEvent(new MyApplicationEvent("我的事件"));
    context.close();
}
```

**打印日志**

```latex
org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@2e0fa5d3]
com.example.fuzy.listener.MyApplicationEvent[source=我的事件]
org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@2e0fa5d3]
```

## 2、源码分析

初始化`AnnotationConfigApplicationContext`时通过`registerListeners()`方法注册所有监听器

**发布事件源码分析**

- `applicationContext.publishEvent(ApplicationEvent e)`调用`AbstractApplicationContext`中的`publishEvent`方法
- `multicastEvent` 遍历所有合适的`applicationListener`，执行invoke(listener,event)方法
- 触发`listener.onApplicationEvent()`方法，如果是实现了`ApplicationListener`接口，则直接调用其中的`onApplicationEvent()`方法；如果是用`@EventListener`注释，则调用`ApplicationListenerMethodAdapter`中的`onApplicationEvent()`方法。

`org.springframework.context.support.AbstractApplicationContext#publishEvent`

```java
protected void publishEvent(Object event, @Nullable ResolvableType eventType) {
    
    ApplicationEvent applicationEvent;
    if (event instanceof ApplicationEvent) {
        applicationEvent = (ApplicationEvent) event;
    }else {
        applicationEvent = new PayloadApplicationEvent<>(this, event);
        if (eventType == null) {
            eventType = ((PayloadApplicationEvent<?>) applicationEvent).getResolvableType();
        }
    }

    // Multicast right now if possible - or lazily once the multicaster is initialized
    if (this.earlyApplicationEvents != null) {
        this.earlyApplicationEvents.add(applicationEvent);
    }else {
        //正常的情况走这部逻辑
        getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
    }

    // Publish event via parent context as well...
    if (this.parent != null) {
        if (this.parent instanceof AbstractApplicationContext) {
            ((AbstractApplicationContext) this.parent).publishEvent(event, eventType);
        }
        else {
            this.parent.publishEvent(event);
        }
    }
}
```

`org.springframework.context.event.SimpleApplicationEventMulticaster#multicastEvent`

```java
public void multicastEvent(final ApplicationEvent event, @Nullable ResolvableType eventType) {
    ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
    Executor executor = getTaskExecutor();
    for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
        if (executor != null) {
            //获取匹配的监听器
            executor.execute(() -> invokeListener(listener, event));
        }else {
            //获取匹配的监听器
            invokeListener(listener, event);
        }
    }
}
```

`org.springframework.context.event.SimpleApplicationEventMulticaster#invokeListener`

```java
protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
    ErrorHandler errorHandler = getErrorHandler();
    if (errorHandler != null) {
        try {
            doInvokeListener(listener, event);
        }
        catch (Throwable err) {
            errorHandler.handleError(err);
        }
    }
    else {
        doInvokeListener(listener, event);
    }
}
```

`org.springframework.context.event.SimpleApplicationEventMulticaster#doInvokeListener`:调用事件监听器的`onApplicationEvent`方法

```java
private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
    ...
    listener.onApplicationEvent(event);
    ... 
}
```





 


# `ApplicationListener`（监听事件）

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
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event);
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


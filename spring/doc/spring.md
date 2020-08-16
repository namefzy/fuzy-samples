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





 


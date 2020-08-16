# `dubbo-服务导出`

## 1、`Quick Start`

- `jar`包引入

```xml
<dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.7.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.14</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>4.2.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>4.2.0</version>
        </dependency>
```

- 配置文件

```yaml
spring:
  application:
    name: spring-cloud-dubbo-provider
dubbo:
  application:
    #发布的服务名称
    name: dubbo-provider
  protocol:
    #dubbo协议
    name: dubbo
    #dubbo默认的端口号
    port: 20880
  registry:
    #zookeeper注册地址
    address: zookeeper://127.0.0.1:2181
    timeout: 25000
server:
  port: 0
```



- 代码

**接口**

```java
public interface OrderService {
    String getOrderByOrderCode(String orderCode);
}
```

**实现类**

```java
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${server.port}")
    private int port;
    @Override
    public String getOrderByOrderCode(String orderCode) {

        return String.format("OrderCode=%s;port=%s",orderCode,port);
    }
}
```

**启动类**

```java
//DubboComponentScan扫描@Service（非spring中Service注解）的类
@DubboComponentScan
@SpringBootApplication
public class DubboClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DubboClientApplication.class)
                .run(args);
        //测试Dubbo中的OrderService有没有注册到IoC容器中
		//输出：ServiceBean:com.fuzy.example.service.OrderService,服务发布所需
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.err.println(beanDefinitionNames[i]);
        }
    }
}
```

## 2、源码分析

​	本次源码分析是基于`dubbo2.7.5`版本，我们就从`SpringBoot`的自动装配开始分析`dubbo`的服务发布流程。

首先从`DubboComponentScan`注解开始分析。

### 注册

`org.apache.dubbo.config.spring.context.annotation.DubboComponentScan`

```java
/**
 *从dubbo2.5.7开始扫描所有dubbo注解组件并注册为spring bean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DubboComponentScanRegistrar.class)
public @interface DubboComponentScan {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
```

​	由注解中的`@Import`注解分析`DubboComponentScanRegistrar`类，该类的作用是注册所有带有`dubbo`注解（`Service`和`Reference`）的类；

`org.apache.dubbo.config.spring.context.annotation.DubboComponentScanRegistrar`

```java
public class DubboComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取扫描包路径
        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
        //注册@Service注解的bean
        registerServiceAnnotationBeanPostProcessor(packagesToScan, registry);
	    //注册@Reference注解的bean
        registerReferenceAnnotationBeanPostProcessor(registry);
    }

    /**
     * 注册 ServiceAnnotationBeanPostProcessor
     */
    private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = rootBeanDefinition(ServiceAnnotationBeanPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        //注册ServiceAnnotationBeanPostProcessor
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }

    /**
     * 注册 {@link ReferenceAnnotationBeanPostProcessor} into {@link BeanFactory}
     */
    private void registerReferenceAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {

        registerInfrastructureBean(registry,
                ReferenceAnnotationBeanPostProcessor.BEAN_NAME, ReferenceAnnotationBeanPostProcessor.class);

    }

    /**
     * 获得扫描包路径
     */
    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(DubboComponentScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        String[] value = attributes.getStringArray("value");
        // Appends value array attributes
        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }

}
```

​	注册`ServiceAnnotationBeanPostProcessor`实例化后，会调用`postProcessBeanDefinitionRegistry`;

`org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor`

```java
public class ServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware,
        ResourceLoaderAware, BeanClassLoaderAware {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // @since 2.7.5 当Application执行了ContextRefreshedEvent和ContextClosedEvent事件后，dubbo服务也对应着刷新状态
        registerBeans(registry, DubboBootstrapApplicationListener.class);
        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

        if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {
            //注册OrderService 到Spring容器中，为了之后发布服务获取数据
            //测试在启动类中获取所有IoC中的实例，可以输出ServiceBean:com.fuzy.example.service.OrderService
            registerServiceBeans(resolvedPackagesToScan, registry);
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("packagesToScan is empty , ServiceBean registry will be ignored!");
            }
        }

    }        
}
```

### 发布






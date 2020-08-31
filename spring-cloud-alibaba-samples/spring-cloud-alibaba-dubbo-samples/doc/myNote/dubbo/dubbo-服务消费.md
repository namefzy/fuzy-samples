# `dubbo-服务消费`

## 1、`Quick Start`

- jar包引入

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-actuator</artifactId>
            <version>2.1.6.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.fuzy</groupId>
            <artifactId>api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
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
    name: spring-cloud-dubbo-consumer
dubbo:
  application:
    name: dubbo-consumer
  config-center:
    timeout: 10000
  registry:
    address: zookeeper://192.168.56.101:2181
    timeout: 10000
```

- 启动类

```java
@SpringBootApplication
@RestController
public class DubboConsumerApplication {

    /**
     * url:用于直接调用的服务目标URL（如果已指定），则注册表中心无效。
     * check:在启动过程中检查服务提供商是否可用，默认值为true.
     * loadbalance:负载均衡
     *      random:随机算法（默认）
     *      roundrobin:轮询
     *      leastActive:最小活跃调用数
     * cluster:容错模式
     *      failover:失败自动切换节点重试，默认会重试两次（retries=2）,重试会导致更长的响应，并且可能会导致事务性操作带来重复数据
     *      failfast:快速失败。当服务调用失败，立即报错
     *      failsafe:失败安全。出现异常时，直接忽略
     *      failback:失败后自动回复。服务调用出现异常，在后台记录这条失败的请求定时重发
     *      forking:并行调用集群中的多个服务，只要其中一个成功就返回，可以通过forks设置最大并行数
     *      broadcast:广播调用所有的服务提供者，任意一个服务报错则表示服务调用失败
     * mock:服务降级；当服务提供方出现网络异常无法访问时，客户端不抛出异常，而是通过降级配置返回兜底数据。
     *      例如：远程服务挂了，此时通过服务降级返回友好提示数据
     */
    @Reference
    private OrderService orderService;

    @Bean
    public ApplicationRunner runner(){
        return args -> System.out.println(orderService.getOrderByOrderCode("M12343434"));
    }

    @GetMapping("/user")
    public String getUserWithOrder(){
        String orderCode = "M1234";
        return orderService.getOrderByOrderCode(orderCode);
    }
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class,args);
    }
}
```

## 2、`dubbo`服务消费具体细节

### 引用服务

#### a、引用直连服务

​	在没有注册中心，直连提供者的情况下 [[3\]](http://dubbo.apache.org/zh-cn/docs/dev/implementation.html#fn3)，`ReferenceConfig` 解析出的 URL 的格式为：`dubbo://service-host/com.fuzy.example.service.OrderService?version=1.0.0`。

​	基于扩展点自适应机制，通过 URL 的 `dubbo://` 协议头识别，直接调用 `DubboProtocol` 的 `refer()` 方法，返回提供者引用。

#### b、从注册中心发现引用服务

​	在有注册中心，通过注册中心发现提供者地址的情况下 [[4\]](http://dubbo.apache.org/zh-cn/docs/dev/implementation.html#fn4)，`ReferenceConfig` 解析出的 URL 的格式为： `registry://registry-host/org.apache.dubbo.registry.RegistryService?refer=URL.encode("consumer://consumer-host/com.fuzy.example.service.OrderService?version=1.0.0")`。

​	基于扩展点自适应机制，通过 URL 的 `registry://` 协议头识别，就会调用 `RegistryProtocol` 的 `refer()` 方法，基于 `refer` 参数中的条件，查询提供者 URL，如： `dubbo://service-host/com.fuzy.example.service.OrderService?version=1.0.0`。

​	基于扩展点自适应机制，通过提供者 URL 的 `dubbo://` 协议头识别，就会调用 `DubboProtocol` 的 `refer()` 方法，得到提供者引用。

然后 `RegistryProtocol` 将多个提供者引用，通过 `Cluster` 扩展点，伪装成单个提供者引用返回。

#### c、服务消费详情过程

![/dev-guide/images/dubbo_rpc_refer.jpg](http://dubbo.apache.org/docs/zh-cn/dev/sources/images/dubbo_rpc_refer.jpg)

上图是服务消费的主过程：

​	首先 `ReferenceConfig` 类的 `init` 方法调用 `Protocol` 的 `refer` 方法生成 `Invoker` 实例(如上图中的红色部分)，这是服务消费的关键。接下来把 `Invoker` 转换为客户端需要的接口(如：HelloWorld)。

http://dubbo.apache.org/zh-cn/docs/dev/implementation.html

## 3、源码分析

### 服务消费入口

在`DubboAutoConfiguration`自动装配类中有这样一段代码：

```java
ConditionalOnProperty(prefix = DUBBO_PREFIX, name = "enabled", matchIfMissing = true)
@Configuration
@AutoConfigureAfter(DubboRelaxedBindingAutoConfiguration.class)
@EnableConfigurationProperties(DubboConfigurationProperties.class)
public class DubboAutoConfiguration {
	//省略其他代码
    @ConditionalOnMissingBean
    @Bean(name = ReferenceAnnotationBeanPostProcessor.BEAN_NAME)
    public ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor() {
        //会将ReferenceAnnotationBeanPostProcessor加载到IOC容器中
        return new ReferenceAnnotationBeanPostProcessor();
    }
}

```

`ReferenceAnnotationBeanPostProcessor`类图：

![ReferenceAnnotationBeanPostProcessor类图](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20200831231846.png)

- `AbstractAnnotationBeanPostProcessor`:初始化后调用`ReferenceAnnotationBeanPostProcessor()`方法
- `ApplicationContextAware`:设置上下文对象
- `ApplicationListener`：监听事件

```java
public class ReferenceAnnotationBeanPostProcessor extends AbstractAnnotationBeanPostProcessor implements
        ApplicationContextAware, ApplicationListener {
    //兼容老版本注解
    public ReferenceAnnotationBeanPostProcessor() {
        super(Reference.class, com.alibaba.dubbo.config.annotation.Reference.class);
    }
    //设置上下文
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //事件监听-服务发布的入口
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ServiceBeanExportedEvent) {
            onServiceBeanExportEvent((ServiceBeanExportedEvent) event);
        }
    }
}
```


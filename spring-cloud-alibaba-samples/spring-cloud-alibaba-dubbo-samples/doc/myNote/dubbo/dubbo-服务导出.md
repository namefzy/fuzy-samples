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

## 2、`Dubbo服务注册具体细节`

#### 解析服务

​	在 `ServiceConfig.export()` 或 `ReferenceConfig.get()` 初始化时，将 Bean 对象转换 URL 格式，所有 Bean 属性转成 URL 的参数。然后将 URL 传给 [协议扩展点](http://dubbo.apache.org/zh-cn/docs/dev/impls/protocol.html)，基于扩展点的 [扩展点自适应机制](http://dubbo.apache.org/zh-cn/docs/dev/SPI.html)，根据 URL 的协议头，进行不同协议的服务暴露或引用。

#### 暴露服务

##### a、只暴露服务端口

​	在没有注册中心，直接暴露提供者的情况下 [[1\]](http://dubbo.apache.org/zh-cn/docs/dev/implementation.html#fn1)，`ServiceConfig` 解析出的 URL 的格式为： `dubbo://service-host/com.fuzy.example.service.OrderService?version=1.0.0`。基于扩展点自适应机制，通过 URL 的 `dubbo://` 协议头识别，直接调用 `DubboProtocol`的 `export()` 方法，打开服务端口。

##### b、向注册中心暴露服务

​	在有注册中心，需要注册提供者地址的情况下 [[2\]](http://dubbo.apache.org/zh-cn/docs/dev/implementation.html#fn2)，`ServiceConfig` 解析出的 URL 的格式为: `registry://registry-host/org.apache.dubbo.registry.RegistryService?export=URL.encode("dubbo://service-host/com.fuzy.example.service.OrderService?version=1.0.0")`，

​	基于扩展点自适应机制，通过 URL 的 `registry://` 协议头识别，就会调用 `RegistryProtocol` 的 `export()` 方法，将 `export` 参数中的提供者 URL，先注册到注册中心。

​	再重新传给 `Protocol` 扩展点进行暴露： `dubbo://service-host/com.fuzy.example.service.OrderService?version=1.0.0`，然后基于扩展点自适应机制，通过提供者 URL 的 `dubbo://` 协议头识别，就会调用 `DubboProtocol` 的 `export()` 方法，打开服务端口。

##### c、拦截服务

​	基于扩展点自适应机制，所有的 `Protocol` 扩展点都会自动套上 `Wrapper` 类。

​	基于 `ProtocolFilterWrapper` 类，将所有 `Filter` 组装成链，在链的最后一节调用真实的引用。

​	基于 `ProtocolListenerWrapper` 类，将所有 `InvokerListener` 和 `ExporterListener` 组装集合，在暴露和引用前后，进行回调。

​	包括监控在内，所有附加功能，全部通过 `Filter` 拦截实现。

##### d、服务提供者暴露服务的详细过程

![/dev-guide/images/dubbo_rpc_export.jpg](http://dubbo.apache.org/docs/zh-cn/dev/sources/images/dubbo_rpc_export.jpg)

上图是服务提供者暴露服务的主过程：

​	首先 `ServiceConfig` 类拿到对外提供服务的实际类 ref(如：HelloWorldImpl),然后通过 `ProxyFactory` 类的 `getInvoker` 方法使用 ref 生成一个 `AbstractProxyInvoker` 实例，到这一步就完成具体服务到 `Invoker` 的转化。接下来就是 `Invoker` 转换到 `Exporter` 的过程。

​	Dubbo 处理服务暴露的关键就在 `Invoker` 转换到 `Exporter` 的过程，上图中的红色部分。下面我们以 Dubbo 和 RMI 这两种典型协议的实现来进行说明：

Dubbo 的实现

​	Dubbo 协议的 `Invoker` 转为 `Exporter` 发生在 `DubboProtocol` 类的 `export` 方法，它主要是打开 socket 侦听服务，并接收客户端发来的各种请求，通讯细节由 Dubbo 自己实现。

RMI 的实现

​	RMI 协议的 `Invoker` 转为 `Exporter` 发生在 `RmiProtocol`类的 `export` 方法，它通过 Spring 或 Dubbo 或 JDK 来实现 RMI 服务，通讯细节这一块由 JDK 底层来实现，这就省了不少工作量。

#### Invoker

​	在服务服务流程中有这样一段代码：`Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(EXPORT_KEY, url.toFullString()));`在`spi`机制下最终对调用`JavassistProxyFactory.getInvoker`方法：

```java
public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
    // TODO Wrapper cannot handle this scenario correctly: the classname contains '$'
    final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
    //proxy:接口的实现
    //type:接口全称
   // url:协议地址：registry://...
    return new AbstractProxyInvoker<T>(proxy, type, url) {
        @Override
        protected Object doInvoke(T proxy, String methodName,
                                  Class<?>[] parameterTypes,
                                  Object[] arguments) throws Throwable {
            return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
        }
    };
}
```

​	通过断点的方式在 `Wrapper.getWrapper `中的` makeWrapper`，会创建一个动态代理，核心的方法 `invokeMethod`代码如下:

```java
public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException {
 	cn.fuzy.example.OrderService w;
 	try {
 		w = ((cn.fuzy.example.ISayHelloService) $1);
     } catch (Throwable e) {
     	throw new IllegalArgumentException(e);
     }
     try {
         if ("getOrderByOrderCode".equals($2) && $3.length == 1) {
         	return ($w) w.sayHello((java.lang.String) $4[0]);
         }
     } catch (Throwable e) {
     	throw new java.lang.reflect.InvocationTargetException(e);
     }
     throw new org.apache.dubbo.common.bytecode.NoSuchMethodException("Not found method \"" + $2 + "\" in class cn.fuzy.example.ISayHelloService.");
 }

```

​	构建好了代理类之后，返回一个`AbstractproxyInvoker`,并且它实现了`doInvoke`方法，这个地方似乎看到了`dubbo`消费者调用过 来的时候触发的影子，因为`wrapper.invokeMethod`本质上就是触发上面动态代理类的方法`invokeMethod`。所以，简单总结一下`Invoke`本质上应该是一个代理，经过层层包装最终进行了发布。当消费者发起请求的时候，会获得这个`invoker`进行调用。

http://dubbo.apache.org/zh-cn/docs/dev/implementation.html

## 3、源码分析

​	本次源码分析是基于`dubbo2.7.5`版本，我们就从`SpringBoot`的自动装配开始分析`dubbo`的服务发布流程。

首先从`DubboComponentScan`注解开始分析。

### `DubboComponentScan`注解解析流程

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

​	

### 注册`ServiceBean`

​	`ServiceAnnotationBeanPostProcessor`实例化后，会调用`postProcessBeanDefinitionRegistry`方法

`org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor`

```java
public class ServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware,
        ResourceLoaderAware, BeanClassLoaderAware {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // @since 2.7.5 当Application执行了ContextRefreshedEvent和ContextClosedEvent事件后，dubbo服务也对应着刷新状态,同时
        registerBeans(registry, DubboBootstrapApplicationListener.class);
        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

        if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {
            //核心逻辑 完成注册ServiceBean
            registerServiceBeans(resolvedPackagesToScan, registry);
        } else {
            if (logger.isWarnEnabled()) {
                logger.warn("packagesToScan is empty , ServiceBean registry will be ignored!");
            }
        }

    }        
}
```

`org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor#registerServiceBeans`

```java
private void registerServiceBeans(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

    DubboClassPathBeanDefinitionScanner scanner =
        new DubboClassPathBeanDefinitionScanner(registry, environment, resourceLoader);

    BeanNameGenerator beanNameGenerator = resolveBeanNameGenerator(registry);

    scanner.setBeanNameGenerator(beanNameGenerator);

    scanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
	//兼容老版本的注解com.alibaba.dubbo.config.annotation.Service.class
    scanner.addIncludeFilter(new AnnotationTypeFilter(com.alibaba.dubbo.config.annotation.Service.class));

    for (String packageToScan : packagesToScan) {
        // Registers @Service Bean first
        scanner.scan(packageToScan);
        // Finds all BeanDefinitionHolders of @Service whether @ComponentScan scans or not.
        Set<BeanDefinitionHolder> beanDefinitionHolders =
            findServiceBeanDefinitionHolders(scanner, packageToScan, registry, beanNameGenerator);

        if (!CollectionUtils.isEmpty(beanDefinitionHolders)) {

            for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
                //注册ServiceBean
                registerServiceBean(beanDefinitionHolder, registry, scanner);
            }
            ...
        } else {
		   ...
        }

    }

}
```

`org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor#registerServiceBean`

```java
private void registerServiceBean(BeanDefinitionHolder beanDefinitionHolder, BeanDefinitionRegistry registry,
                                     DubboClassPathBeanDefinitionScanner scanner) {
    //class com.fuzy.example.service.impl.OrderServiceImpl
    Class<?> beanClass = resolveClass(beanDefinitionHolder);
    //获取注解信息
    Annotation service = findServiceAnnotation(beanClass);
    AnnotationAttributes serviceAnnotationAttributes = getAnnotationAttributes(service, false, false);
    Class<?> interfaceClass = resolveServiceInterfaceClass(serviceAnnotationAttributes, beanClass);

    String annotatedServiceBeanName = beanDefinitionHolder.getBeanName();

    AbstractBeanDefinition serviceBeanDefinition =
        buildServiceBeanDefinition(service, serviceAnnotationAttributes, interfaceClass, annotatedServiceBeanName);

    // ServiceBean Bean name
    String beanName = generateServiceBeanName(serviceAnnotationAttributes, interfaceClass);

    if (scanner.checkCandidate(beanName, serviceBeanDefinition)) {
        //注册ServiceBean到IoC容器中,key=ServiceBean:com.fuzy.example.service.OrderService,value=org.apache.dubbo.config.spring.ServiceBean
        registry.registerBeanDefinition(beanName, serviceBeanDefinition);
		...
    } else {
		...
    }

}
```

### 服务发布

​	`ServiceBean`初始化后的构造方法中并没有发现相关**服务发布**代码，`ServiceBean`部分代码如下:

```java
public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean,
        ApplicationContextAware, BeanNameAware,
        ApplicationEventPublisherAware {
    public ServiceBean() {
        super();
        this.service = null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(getPath())) {
            if (StringUtils.isNotEmpty(beanName)
                && StringUtils.isNotEmpty(getInterface())
                && beanName.startsWith(getInterface())) {
                setPath(beanName);
            }
        }
    }
    ......//省略部分代码
}

```

​	从`ServiceBean`代码中找不到**服务发布**的入口，百思不得其解。所以又翻看了之前的源码，惊喜的发现在`org.apache.dubbo.config.spring.beans.factory.annotation.ServiceAnnotationBeanPostProcessor#postProcessBeanDefinitionRegistry`方法中执行了`registerBeans(registry, DubboBootstrapApplicationListener.class)`注册监听事件，而`DubboBootstrapApplicationListener`类代码如下：

```java
public class DubboBootstrapApplicationListener extends OneTimeExecutionApplicationContextEventListener
        implements Ordered {

    private final DubboBootstrap dubboBootstrap;

    public DubboBootstrapApplicationListener() {
        this.dubboBootstrap = DubboBootstrap.getInstance();
    }
	
    //初始化后调用
    @Override
    public void onApplicationContextEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            //服务发布或者刷新的时候调用
            onContextRefreshedEvent((ContextRefreshedEvent) event);
        } else if (event instanceof ContextClosedEvent) {
            //服务关闭的时候调用
            onContextClosedEvent((ContextClosedEvent) event);
        }
    }

    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        //服务发布
        dubboBootstrap.start();
    }

    private void onContextClosedEvent(ContextClosedEvent event) {
        dubboBootstrap.stop();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
```

​	所以此时服务发布的代码就要以从`dubboBootstrap.start()`为入口进行分析。

`org.apache.dubbo.config.bootstrap.DubboBootstrap#start`:服务发布的入口

```java
public DubboBootstrap start() {
    //自旋 except =false, update=true
    if (started.compareAndSet(false, true)) {
        initialize();
        if (logger.isInfoEnabled()) {
            logger.info(NAME + " is starting...");
        }
        // 1. export Dubbo Services 发布服务的核心代码
        exportServices();

        // Not only provider register
        if (!isOnlyRegisterProvider() || hasExportedServices()) {
            // 2. export MetadataService
            exportMetadataService();
            //3. Register the local ServiceInstance if required
            registerServiceInstance();
        }

        referServices();

        if (logger.isInfoEnabled()) {
            logger.info(NAME + " has started.");
        }
    }
    return this;
}
```

`org.apache.dubbo.config.bootstrap.DubboBootstrap#exportServices`

> `dubbo协议ServiceBean:<dubbo:service beanName="ServiceBean:com.fuzy.example.service.OrderService" />`
>
> `rest协议ServiceBean:<rest:service beanName="ServiceBean:com.fuzy.example.service.OrderService" />`
>
> 一个`dubbo`服务需要发布几次，取决于协议的配置数，如果一个`dubbo`服务配置了3个协议，`rest、webservice、dubbo`。

```java
private void exportServices() {
    //ServiceBean:<dubbo:service beanName="ServiceBean:com.fuzy.example.service.OrderService" />
    //一个接口构成一个ServiceBean
    configManager.getServices().forEach(sc -> {
        // TODO, compatible with ServiceConfig.export()
        ServiceConfig serviceConfig = (ServiceConfig) sc;
        serviceConfig.setBootstrap(this);

        if (exportAsync) {
            ExecutorService executor = executorRepository.getServiceExporterExecutor();
            Future<?> future = executor.submit(() -> {
                //异步
                sc.export();
            });
            asyncExportingFutures.add(future);
        } else {
            //同步
            sc.export();
            exportedServices.add(sc);
        }
    });
}
```

`org.apache.dubbo.config.ServiceConfig#export`

```java
public synchronized void export() {
    if (!shouldExport()) {
        return;
    }

    if (bootstrap == null) {
        bootstrap = DubboBootstrap.getInstance();
        bootstrap.init();
    }

    checkAndUpdateSubConfigs();

    //初始化源数据
    serviceMetadata.setVersion(version);
    serviceMetadata.setGroup(group);
    serviceMetadata.setDefaultGroup(group);
    serviceMetadata.setServiceType(getInterfaceClass());
    serviceMetadata.setServiceInterfaceName(getInterface());
    serviceMetadata.setTarget(getRef());
	//是否延迟发布
    if (shouldDelay()) {
        DELAY_EXPORT_EXECUTOR.schedule(this::doExport, getDelay(), TimeUnit.MILLISECONDS);
    } else {
        //服务发布
        doExport();
    }
}
```

`org.apache.dubbo.config.ServiceConfig#doExport`

```java
protected synchronized void doExport() {
    ......
    doExportUrls();

    // dispatch a ServiceConfigExportedEvent since 2.7.4
    dispatch(new ServiceConfigExportedEvent(this));
}
```

`org.apache.dubbo.config.ServiceConfig#doExportUrls`

```java
private void doExportUrls() {
    ServiceRepository repository = ApplicationModel.getServiceRepository();
    ServiceDescriptor serviceDescriptor = repository.registerService(getInterfaceClass());
    repository.registerProvider(
        getUniqueServiceName(),
        ref,
        serviceDescriptor,
        this,
        serviceMetadata
    );
	//registry://127.0.0.1:2181/org.apache.dubbo.registry.RegistryService?application=dubbo-provider&dubbo=2.0.2&pid=13160&qos.enable=false&registry=zookeeper&release=2.7.5&timeout=25000&timestamp=1598363362970
    List<URL> registryURLs = ConfigValidationUtils.loadRegistries(this, true);

    for (ProtocolConfig protocolConfig : protocols) {
        String pathKey = URL.buildKey(getContextPath(protocolConfig)
                                      .map(p -> p + "/" + path)
                                      .orElse(path), group, version);
        repository.registerService(pathKey, interfaceClass);
        serviceMetadata.setServiceKey(pathKey);
        //protocolConfig:<dubbo:protocol name="dubbo" port="20880" valid="true" id="dubbo" prefix="dubbo.protocols." />
        doExportUrlsFor1Protocol(protocolConfig, registryURLs);
    }
}
```

`org.apache.dubbo.config.ServiceConfig#doExportUrlsFor1Protocol`

```java
private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
    String name = protocolConfig.getName();
    //获取协议名称默认为dubbo
    if (StringUtils.isEmpty(name)) {
        name = DUBBO;
    }

    Map<String, String> map = new HashMap<String, String>();
    ......//省略初始化map流程，也就是构建url流程
    // export service
    String host = findConfigedHosts(protocolConfig, registryURLs, map);
    Integer port = findConfigedPorts(protocolConfig, name, map);
    //dubbo://192.168.199.203:20880/com.fuzy.example.service.OrderService?anyhost=true&application=dubbo-provider&bind.ip=192.168.199.203&bind.port=20880&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=com.fuzy.example.service.OrderService&methods=getOrderByOrderCode&pid=13160&qos.enable=false&release=2.7.5&side=provider&timestamp=1598363447282
    URL url = new URL(name, host, port, getContextPath(protocolConfig).map(p -> p + "/" + path).orElse(path), map);

    // You can customize Configurator to append extra parameters
    if (ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
        .hasExtension(url.getProtocol())) {
        url = ExtensionLoader.getExtensionLoader(ConfiguratorFactory.class)
            .getExtension(url.getProtocol()).getConfigurator(url).configure(url);
    }

    String scope = url.getParameter(SCOPE_KEY);
    // don't export when none is configured
    if (!SCOPE_NONE.equalsIgnoreCase(scope)) {

        //injvm 发布到本地
        if (!SCOPE_REMOTE.equalsIgnoreCase(scope)) {
            exportLocal(url);
        }
        //发布远程服务
        if (!SCOPE_LOCAL.equalsIgnoreCase(scope)) {
            if (CollectionUtils.isNotEmpty(registryURLs)) {
                for (URL registryURL : registryURLs) {
                    //if protocol is only injvm ,not register
                    if (LOCAL_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
                        continue;
                    }
                    url = url.addParameterIfAbsent(DYNAMIC_KEY, registryURL.getParameter(DYNAMIC_KEY));
                    URL monitorUrl = ConfigValidationUtils.loadMonitor(this, registryURL);
                    if (monitorUrl != null) {
                        url = url.addParameterAndEncoded(MONITOR_KEY, monitorUrl.toFullString());
                    }
                    if (logger.isInfoEnabled()) {
                        if (url.getParameter(REGISTER_KEY, true)) {
                            logger.info("Register dubbo service " + interfaceClass.getName() + " url " + url + " to registry " + registryURL);
                        } else {
                            logger.info("Export dubbo service " + interfaceClass.getName() + " to url " + url);
                        }
                    }

                    String proxy = url.getParameter(PROXY_KEY);
                    if (StringUtils.isNotEmpty(proxy)) {
                        registryURL = registryURL.addParameter(PROXY_KEY, proxy);
                    }
				  //invoker Dubbo的核心模型
                    Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(EXPORT_KEY, url.toFullString()));
                    DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);
					
                    Exporter<?> exporter = protocol.export(wrapperInvoker);
                    exporters.add(exporter);
                }
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("Export dubbo service " + interfaceClass.getName() + " to url " + url);
                }
                Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, url);
                DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);
			   //此时的wrappinerInvoker url为registry:// ,所以会调用Registry方法
                Exporter<?> exporter = protocol.export(wrappinerInvoker);
                exporters.add(exporter);
            }
            /**
                 * @since 2.7.0
                 * ServiceData Store
                 */
            WritableMetadataService metadataService = WritableMetadataService.getExtension(url.getParameter(METADATA_KEY, DEFAULT_METADATA_STORAGE_TYPE));
            if (metadataService != null) {
                metadataService.publishServiceDefinition(url);
            }
        }
    }
    this.urls.add(url);
}
```

`org.apache.dubbo.registry.integration.RegistryProtocol#export`:实现服务注册

> - 实现对应协议的服务发布 
> - 实现服务注册 
> -  订阅服务重写

```java
//originInvoker:registry协议
public <T> Exporter<T> export(final Invoker<T> originInvoker) throws RpcException {
    //zookeeper://协议 
    URL registryUrl = getRegistryUrl(originInvoker);
    //dubbo://协议
    URL providerUrl = getProviderUrl(originInvoker);

    final URL overrideSubscribeUrl = getSubscribedOverrideUrl(providerUrl);
    final OverrideListener overrideSubscribeListener = new OverrideListener(overrideSubscribeUrl, originInvoker);
    overrideListeners.put(overrideSubscribeUrl, overrideSubscribeListener);

    providerUrl = overrideUrlWithConfig(providerUrl, overrideSubscribeListener);
    //这里就交给了具体的协议去暴露服务
    final ExporterChangeableWrapper<T> exporter = doLocalExport(originInvoker, providerUrl);

    final Registry registry = getRegistry(originInvoker);
    final URL registeredProviderUrl = getUrlToRegistry(providerUrl, registryUrl);
    boolean register = providerUrl.getParameter(REGISTER_KEY, true);
    if (register) {
        //注册服务
        register(registryUrl, registeredProviderUrl);
    }

    registry.subscribe(overrideSubscribeUrl, overrideSubscribeListener);

    exporter.setRegisterUrl(registeredProviderUrl);
    exporter.setSubscribeUrl(overrideSubscribeUrl);
    return new DestroyableExporter<>(exporter);
}
```

`org.apache.dubbo.registry.integration.RegistryProtocol#doLocalExport`:导出`Invoker`

```java
private <T> ExporterChangeableWrapper<T> doLocalExport(final Invoker<T> originInvoker, URL providerUrl) {
    String key = getCacheKey(originInvoker);

    return (ExporterChangeableWrapper<T>) bounds.computeIfAbsent(key, s -> {
        //invokerDelegate dubbo协议
        Invoker<?> invokerDelegate = new InvokerDelegate<>(originInvoker, providerUrl);
        ////将 invoker 转换为 exporter 并启动 netty 服务
        return new ExporterChangeableWrapper<>((Exporter<T>) protocol.export(invokerDelegate), originInvoker);
    });
}
```

​	`InvokerDelegete: 是 RegistryProtocol 的一个静态内部类，该类是一个 originInvoker 的委托类，该类存储了 originInvoker，其 父类 InvokerWrapper 还会存储 providerUrl，InvokerWrapper 会调用 originInvoker 的 invoke 方法，也会销毁 invoker。可以 管理 invoker 的生命周期`。

`org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol#export`:启动netty服务

```java
public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
    //真正注册的元素据 dubbo://192.168.199.203:20880/com.fuzy.example.service.OrderService?anyhost=true&application=dubbo-provider&bind.ip=192.168.199.203&bind.port=20880&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=com.fuzy.example.service.OrderService&methods=getOrderByOrderCode&pid=3852&qos.enable=false&release=2.7.5&side=provider&timestamp=1598367873375
    URL url = invoker.getUrl();

    //com.fuzy.example.service.OrderService:20880
    String key = serviceKey(url);
    DubboExporter<T> exporter = new DubboExporter<T>(invoker, key, exporterMap);
    exporterMap.put(key, exporter);

    ....省略部分代码
	//dubbo://192.168.199.203:20880/com.fuzy.example.service.OrderService?anyhost=true&application=dubbo-provider&bind.ip=192.168.199.203&bind.port=20880&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=com.fuzy.example.service.OrderService&methods=getOrderByOrderCode&pid=3852&qos.enable=false&release=2.7.5&side=provider&timestamp=1598367873375
    openServer(url);
    optimizeSerialization(url);

    return exporter;
}
```

`org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol#openServer`:启动netty暴露端口

```java
private void openServer(URL url) {
    String key = url.getAddress();
    //client can export a service which's only for server to invoke
    boolean isServer = url.getParameter(IS_SERVER_KEY, true);
    if (isServer) {
        ProtocolServer server = serverMap.get(key);
        if (server == null) {
            synchronized (this) {
                server = serverMap.get(key);
                if (server == null) {
                    // 创建服务器实例
                    serverMap.put(key, createServer(url));
                }
            }
        } else {
            // server supports reset, use together with override
            server.reset(url);
        }
    }
}
```

`org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol#createServer`:创建服务

```java
private ProtocolServer createServer(URL url) {
    //dubbo://192.168.199.203:20880/com.fuzy.example.service.OrderService?anyhost=true&application=dubbo-provider&bind.ip=192.168.199.203&bind.port=20880&channel.readonly.sent=true&codec=dubbo&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&heartbeat=60000&interface=com.fuzy.example.service.OrderService&methods=getOrderByOrderCode&pid=3852&qos.enable=false&release=2.7.5&side=provider&timestamp=1598367873375
    url = URLBuilder.from(url)
        // send readonly event when server closes, it's enabled by default
        .addParameterIfAbsent(CHANNEL_READONLYEVENT_SENT_KEY, Boolean.TRUE.toString())
        // enable heartbeat by default
        .addParameterIfAbsent(HEARTBEAT_KEY, String.valueOf(DEFAULT_HEARTBEAT))
        .addParameter(CODEC_KEY, DubboCodec.NAME)
        .build();
    String str = url.getParameter(SERVER_KEY, DEFAULT_REMOTING_SERVER);

    if (str != null && str.length() > 0 && !ExtensionLoader.getExtensionLoader(Transporter.class).hasExtension(str)) {
        throw new RpcException("Unsupported server type: " + str + ", url: " + url);
    }

    ExchangeServer server;
    try {
        //绑定服务器
        server = Exchangers.bind(url, requestHandler);
    } catch (RemotingException e) {
        throw new RpcException("Fail to start server(url: " + url + ") " + e.getMessage(), e);
    }

    str = url.getParameter(CLIENT_KEY);
    if (str != null && str.length() > 0) {
        Set<String> supportedTypes = ExtensionLoader.getExtensionLoader(Transporter.class).getSupportedExtensions();
        if (!supportedTypes.contains(str)) {
            throw new RpcException("Unsupported client type: " + str);
        }
    }

    return new DubboProtocolServer(server);
}
```

中间省略一些调用路径，最终执行监听服务端口的方法：

`org.apache.dubbo.remoting.transport.AbstractServer#doOpen`:实现类调用抽象类的具体实现，开启netty服务

```java
protected void doOpen() throws Throwable {
        bootstrap = new ServerBootstrap();

        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
        workerGroup = new NioEventLoopGroup(getUrl().getPositiveParameter(IO_THREADS_KEY, Constants.DEFAULT_IO_THREADS),
                new DefaultThreadFactory("NettyServerWorker", true));

        final NettyServerHandler nettyServerHandler = new NettyServerHandler(getUrl(), this);
        channels = nettyServerHandler.getChannels();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // FIXME: should we use getTimeout()?
                        int idleTimeout = UrlUtils.getIdleTimeout(getUrl());
                        NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), getUrl(), NettyServer.this);
                        if (getUrl().getParameter(SSL_ENABLED_KEY, false)) {
                            ch.pipeline().addLast("negotiation",
                                    SslHandlerInitializer.sslServerHandler(getUrl(), nettyServerHandler));
                        }
                        ch.pipeline()
                                .addLast("decoder", adapter.getDecoder())
                                .addLast("encoder", adapter.getEncoder())
                                .addLast("server-idle-handler", new IdleStateHandler(0, 0, idleTimeout, MILLISECONDS))
                                .addLast("handler", nettyServerHandler);
                    }
                });
        // bind
        ChannelFuture channelFuture = bootstrap.bind(getBindAddress());
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();

    }
```

### 服务注册

`org.apache.dubbo.registry.integration.RegistryProtocol#register`:注册服务

```java
public void register(URL registryUrl, URL registeredProviderUrl) {
    //registryUrl 为zookeeper协议 由于zookeeper方法中并没有具体实现，所以只有去父类方法中查找
    Registry registry = registryFactory.getRegistry(registryUrl);
    //registeredProviderUrl dubbo协议
    registry.register(registeredProviderUrl);

    ProviderModel model = ApplicationModel.getProviderModel(registeredProviderUrl.getServiceKey());
    model.addStatedUrl(new ProviderModel.RegisterStatedURL(
        registeredProviderUrl,
        registryUrl,
        true
    ));
}
```

`org.apache.dubbo.registry.support.AbstractRegistryFactory#getRegistry(org.apache.dubbo.common.URL)`

```java
@Override
public Registry getRegistry(URL url) {
    url = URLBuilder.from(url)
        .setPath(RegistryService.class.getName())
        .addParameter(INTERFACE_KEY, RegistryService.class.getName())
        .removeParameters(EXPORT_KEY, REFER_KEY)
        .build();
    String key = url.toServiceStringWithoutResolving();
    // Lock the registry access process to ensure a single instance of the registry
    LOCK.lock();
    try {
        Registry registry = REGISTRIES.get(key);
        if (registry != null) {
            return registry;
        }
        //创建一个 zookeeperRegistry注册中心
        registry = createRegistry(url);
        if (registry == null) {
            throw new IllegalStateException("Can not create registry " + url);
        }
        REGISTRIES.put(key, registry);
        return registry;
    } finally {
        // Release the lock
        LOCK.unlock();
    }
}
```

`org.apache.dubbo.registry.support.FailbackRegistry#register`:在这个类里面进行服务注册

> 这个方法会调用FailbackRegistry 类中的 register. 为什么呢？因为 ZookeeperRegistry 这个类中并没有 register 这个方法，但是 他的父类 FailbackRegistry 中存在 register 方法，而这个类又重写了 AbstractRegistry 类中的 register 方法。所以我们可以直接 定位大 FailbackRegistry 这个类中的 register 方法中

```java
public void register(URL url) {
    ...
    super.register(url);
    removeFailedRegistered(url);
    removeFailedUnregistered(url);
    try {
        // Sending a registration request to the server side
        //具体的注册逻辑
        doRegister(url);
    } catch (Exception e) {
        Throwable t = e;

        // If the startup detection is opened, the Exception is thrown directly.
        boolean check = getUrl().getParameter(Constants.CHECK_KEY, true)
            && url.getParameter(Constants.CHECK_KEY, true)
            && !CONSUMER_PROTOCOL.equals(url.getProtocol());
        boolean skipFailback = t instanceof SkipFailbackWrapperException;
        if (check || skipFailback) {
            if (skipFailback) {
                t = t.getCause();
            }
            throw new IllegalStateException("Failed to register " + url + " to registry " + getUrl().getAddress() + ", cause: " + t.getMessage(), t);
        } else {
            logger.error("Failed to register " + url + ", waiting for retry, cause: " + t.getMessage(), t);
        }

        // Record a failed registration request to a failed list, retry regularly
        addFailedRegistered(url);
    }
}
```

`org.apache.dubbo.registry.zookeeper.ZookeeperRegistry#doRegister`:zookeeper操作客户端并存储数据

```java
public void doRegister(URL url) {
    ...
    //zookeeper创建节点操作
    zkClient.create(toUrlPath(url), url.getParameter(DYNAMIC_KEY, true));

}
```
















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

## 2、基本配置

**多协议配置**

```xml
 <dubbo:application name="world"  />
    <dubbo:registry id="registry" address="10.20.141.150:9090" username="admin" password="hello1234" />
    <!-- 多协议配置 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <dubbo:protocol name="rmi" port="1099" />
    <!-- 使用dubbo协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.HelloService" version="1.0.0" ref="helloService" protocol="dubbo" />
    <!-- 使用rmi协议暴露服务 -->
    <dubbo:service interface="com.alibaba.hello.api.DemoService" version="1.0.0" ref="demoService" protocol="rmi" /> 
```



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

    List<URL> registryURLs = ConfigValidationUtils.loadRegistries(this, true);

    for (ProtocolConfig protocolConfig : protocols) {
        String pathKey = URL.buildKey(getContextPath(protocolConfig)
                                      .map(p -> p + "/" + path)
                                      .orElse(path), group, version);
        // In case user specified path, register service one more time to map it to path.
        repository.registerService(pathKey, interfaceClass);
        // TODO, uncomment this line once service key is unified
        serviceMetadata.setServiceKey(pathKey);
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

        // export to local if the config is not remote (export to remote only when config is remote)
        if (!SCOPE_REMOTE.equalsIgnoreCase(scope)) {
            exportLocal(url);
        }
        // export to remote if the config is not local (export to local only when config is local)
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

                    // For providers, this is used to enable custom proxy to generate invoker
                    String proxy = url.getParameter(PROXY_KEY);
                    if (StringUtils.isNotEmpty(proxy)) {
                        registryURL = registryURL.addParameter(PROXY_KEY, proxy);
                    }

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

                Exporter<?> exporter = protocol.export(wrapperInvoker);
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














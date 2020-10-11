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

**`ReferenceAnnotationBeanPostProcessor`类：触发事件监听动作，初始化**

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
    
    private void onServiceBeanExportEvent(ServiceBeanExportedEvent event) {
        ServiceBean serviceBean = event.getServiceBean();
        initReferenceBeanInvocationHandler(serviceBean);
    }

    private void initReferenceBeanInvocationHandler(ServiceBean serviceBean) {
        String serviceBeanName = serviceBean.getBeanName();
        ReferenceBeanInvocationHandler handler = localReferenceBeanInvocationHandlerCache.remove(serviceBeanName);
        // 初始化
        if (handler != null) {
            handler.init();
        }
    }

     @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            if (bean == null) { // If the bean is not initialized, invoke init()
                // issue: https://github.com/apache/dubbo/issues/3429
                init();
            }
            result = method.invoke(bean, args);
        } catch (InvocationTargetException e) {
            // re-throws the actual Exception.
            throw e.getTargetException();
        }
        return result;
    }

    private void init() {
        //真正执行初始化的方法
        this.bean = referenceBean.get();
    }
}
}
```

**`ReferenceConfig`类：执行服务消费的逻辑**

`org.apache.dubbo.config.ReferenceConfig#get`

```java
public synchronized T get() {
    if (destroyed) {
        throw new IllegalStateException("The invoker of ReferenceConfig(" + url + ") has already destroyed!");
    }
    if (ref == null) {
        //
        init();
    }
    return ref;
}
```

### 处理配置

`org.apache.dubbo.config.ReferenceConfig#init`

```java
public synchronized void init() {
    if (initialized) {
        return;
    }

    if (bootstrap == null) {
        bootstrap = DubboBootstrap.getInstance();
        bootstrap.init();
    }

    checkAndUpdateSubConfigs();

    //init serivceMetadata
    serviceMetadata.setVersion(version);
    serviceMetadata.setGroup(group);
    serviceMetadata.setDefaultGroup(group);
    serviceMetadata.setServiceType(getActualInterface());
    serviceMetadata.setServiceInterfaceName(interfaceName);
    // TODO, uncomment this line once service key is unified
    serviceMetadata.setServiceKey(URL.buildKey(interfaceName, group, version));

    checkStubAndLocal(interfaceClass);
    ConfigValidationUtils.checkMock(interfaceClass, this);

    Map<String, String> map = new HashMap<String, String>();
    map.put(SIDE_KEY, CONSUMER_SIDE);

    ReferenceConfigBase.appendRuntimeParameters(map);
    if (!ProtocolUtils.isGeneric(generic)) {
        String revision = Version.getVersion(interfaceClass, version);
        if (revision != null && revision.length() > 0) {
            map.put(REVISION_KEY, revision);
        }

        String[] methods = Wrapper.getWrapper(interfaceClass).getMethodNames();
        if (methods.length == 0) {
            logger.warn("No method found in service interface " + interfaceClass.getName());
            map.put(METHODS_KEY, ANY_VALUE);
        } else {
            map.put(METHODS_KEY, StringUtils.join(new HashSet<String>(Arrays.asList(methods)), COMMA_SEPARATOR));
        }
    }
    map.put(INTERFACE_KEY, interfaceName);
    AbstractConfig.appendParameters(map, getMetrics());
    AbstractConfig.appendParameters(map, getApplication());
    AbstractConfig.appendParameters(map, getModule());
    // remove 'default.' prefix for configs from ConsumerConfig
    // appendParameters(map, consumer, Constants.DEFAULT_KEY);
    AbstractConfig.appendParameters(map, consumer);
    AbstractConfig.appendParameters(map, this);
    Map<String, Object> attributes = null;
    if (CollectionUtils.isNotEmpty(getMethods())) {
        attributes = new HashMap<>();
        for (MethodConfig methodConfig : getMethods()) {
            AbstractConfig.appendParameters(map, methodConfig, methodConfig.getName());
            String retryKey = methodConfig.getName() + ".retry";
            if (map.containsKey(retryKey)) {
                String retryValue = map.remove(retryKey);
                if ("false".equals(retryValue)) {
                    map.put(methodConfig.getName() + ".retries", "0");
                }
            }
            ConsumerModel.AsyncMethodInfo asyncMethodInfo = AbstractConfig.convertMethodConfig2AsyncInfo(methodConfig);
            if (asyncMethodInfo != null) {
                attributes.put(methodConfig.getName(), asyncMethodInfo);
            }
        }
    }

    String hostToRegistry = ConfigUtils.getSystemProperty(DUBBO_IP_TO_REGISTRY);
    if (StringUtils.isEmpty(hostToRegistry)) {
        hostToRegistry = NetUtils.getLocalHost();
    } else if (isInvalidLocalHost(hostToRegistry)) {
        throw new IllegalArgumentException("Specified invalid registry ip from property:" + DUBBO_IP_TO_REGISTRY + ", value:" + hostToRegistry);
    }
    map.put(REGISTER_IP_KEY, hostToRegistry);

    serviceMetadata.getAttachments().putAll(map);

    ServiceRepository repository = ApplicationModel.getServiceRepository();
    ServiceDescriptor serviceDescriptor = repository.registerService(interfaceClass);
    repository.registerConsumer(
        serviceMetadata.getServiceKey(),
        attributes,
        serviceDescriptor,
        this,
        null,
        serviceMetadata);
    //创建代理对象
	//map={init=false, side=consumer, application=dubbo-consumer, register.ip=192.168.199.203, release=2.7.5, methods=getOrderByOrderCode, sticky=false, dubbo=2.0.2, pid=1580, interface=com.fuzy.example.service.OrderService, qos.enable=false, timestamp=1599058877524}
    ref = createProxy(map);

    serviceMetadata.setTarget(ref);
    serviceMetadata.addAttribute(PROXY_CLASS_REF, ref);
    repository.lookupReferredService(serviceMetadata.getServiceKey()).setProxyObject(ref);

    initialized = true;

    // dispatch a ReferenceConfigInitializedEvent since 2.7.4
    dispatch(new ReferenceConfigInitializedEvent(this, invoker));
}
```

### 服务引用

`org.apache.dubbo.config.ReferenceConfig#createProxy`：创建代理对象

```java
private T createProxy(Map<String, String> map) {
    if (shouldJvmRefer(map)) {//本地调用
        URL url = new URL(LOCAL_PROTOCOL, LOCALHOST_VALUE, 0, interfaceClass.getName()).addParameters(map);
        invoker = REF_PROTOCOL.refer(interfaceClass, url);
        if (logger.isInfoEnabled()) {
            logger.info("Using injvm service " + interfaceClass.getName());
        }
    } else {
        urls.clear();
        if (url != null && url.length() > 0) { // user specified URL, could be peer-to-peer address, or register center's address.
            String[] us = SEMICOLON_SPLIT_PATTERN.split(url);
            if (us != null && us.length > 0) {
                for (String u : us) {
                    URL url = URL.valueOf(u);
                    if (StringUtils.isEmpty(url.getPath())) {
                        url = url.setPath(interfaceName);
                    }
                    if (UrlUtils.isRegistry(url)) {
                        urls.add(url.addParameterAndEncoded(REFER_KEY, StringUtils.toQueryString(map)));
                    } else {
                        urls.add(ClusterUtils.mergeUrl(url, map));
                    }
                }
            }
        } else {//远程调用
            //registry://192.168.56.101:2181/org.apache.dubbo.registry.RegistryService?application=dubbo-consumer&dubbo=2.0.2&pid=1580&qos.enable=false&registry=zookeeper&release=2.7.5&timeout=10000&timestamp=1599059110313
            if (!LOCAL_PROTOCOL.equalsIgnoreCase(getProtocol())) {
                checkRegistry();
                List<URL> us = ConfigValidationUtils.loadRegistries(this, false);
                if (CollectionUtils.isNotEmpty(us)) {
                    for (URL u : us) {
                        URL monitorUrl = ConfigValidationUtils.loadMonitor(this, u);
                        if (monitorUrl != null) {
                            map.put(MONITOR_KEY, URL.encode(monitorUrl.toFullString()));
                        }
                        //对url进行编码
                        urls.add(u.addParameterAndEncoded(REFER_KEY, StringUtils.toQueryString(map)));
                    }
                }
                if (urls.isEmpty()) {
                    throw new IllegalStateException("No such any registry to reference " + interfaceName + " on the consumer " + NetUtils.getLocalHost() + " use dubbo version " + Version.getVersion() + ", please config <dubbo:registry address=\"...\" /> to your spring config.");
                }
            }
        }

        if (urls.size() == 1) {
            //interfaceClass=com.fuzy.example.service.OrderService
            //url为registry
            //先生成代理类，在调用根据url调用RegistryProtocol方法，调用 RegistryProtocol 的 refer 构建 Invoker 实例
            invoker = REF_PROTOCOL.refer(interfaceClass, urls.get(0));
        } else {// 多个注册中心或多个服务提供者，或者两者混合
            List<Invoker<?>> invokers = new ArrayList<Invoker<?>>();
            URL registryURL = null;
            for (URL url : urls) {
                invokers.add(REF_PROTOCOL.refer(interfaceClass, url));
                if (UrlUtils.isRegistry(url)) {
                    registryURL = url; // use last registry url
                }
            }
            if (registryURL != null) { // registry url is available
                // 如果注册中心链接不为空，则将使用 AvailableCluster
                URL u = registryURL.addParameterIfAbsent(CLUSTER_KEY, ZoneAwareCluster.NAME);
                // The invoker wrap relation would be like: ZoneAwareClusterInvoker(StaticDirectory) -> FailoverClusterInvoker(RegistryDirectory, routing happens here) -> Invoker
                invoker = CLUSTER.join(new StaticDirectory(u, invokers));
            } else {
                // 创建 StaticDirectory 实例，并由 Cluster 对多个 Invoker 进行合并
                invoker = CLUSTER.join(new StaticDirectory(invokers));
            }
        }
    }

    if (shouldCheck() && !invoker.isAvailable()) {
        throw new IllegalStateException("Failed to check the status of the service "
                                        + interfaceName
                                        + ". No provider available for the service "
                                        + (group == null ? "" : group + "/")
                                        + interfaceName +
                                        (version == null ? "" : ":" + version)
                                        + " from the url "
                                        + invoker.getUrl()
                                        + " to the consumer "
                                        + NetUtils.getLocalHost() + " use dubbo version " + Version.getVersion());
    }
    if (logger.isInfoEnabled()) {
        logger.info("Refer dubbo service " + interfaceClass.getName() + " from url " + invoker.getUrl());
    }

    String metadata = map.get(METADATA_KEY);
    WritableMetadataService metadataService = WritableMetadataService.getExtension(metadata == null ? DEFAULT_METADATA_STORAGE_TYPE : metadata);
    if (metadataService != null) {
        URL consumerURL = new URL(CONSUMER_PROTOCOL, map.remove(REGISTER_IP_KEY), 0, map.get(INTERFACE_KEY), map);
        metadataService.publishServiceDefinition(consumerURL);
    }
    // 创建代理
    return (T) PROXY_FACTORY.getProxy(invoker);
}
```

`org.apache.dubbo.registry.integration.RegistryProtocol#refer`：创建Invoker

```java
public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
    //url从registry变成zookeeper
    url = getRegistryUrl(url);
    //registryFactory 根据spi原理调用zookeeper相关的实现类，但是没有具体实现，只有从父类AbstractRegistryFactory找到实现
    Registry registry = registryFactory.getRegistry(url);
    if (RegistryService.class.equals(type)) {
        return proxyFactory.getInvoker((T) registry, type, url);
    }

    // group="a,b" or group="*"
    Map<String, String> qs = StringUtils.parseQueryString(url.getParameterAndDecoded(REFER_KEY));
    String group = qs.get(GROUP_KEY);
    if (group != null && group.length() > 0) {
        if ((COMMA_SPLIT_PATTERN.split(group)).length > 1 || "*".equals(group)) {
            return doRefer(getMergeableCluster(), registry, type, url);
        }
    }
    return doRefer(cluster, registry, type, url);
}
```

`org.apache.dubbo.registry.support.AbstractRegistryFactory#getRegistry(org.apache.dubbo.common.URL)`

```java
public Registry getRegistry(URL url) {
    if (destroyed.get()) {
        LOGGER.warn("All registry instances have been destroyed, failed to fetch any instance. " +
                    "Usually, this means no need to try to do unnecessary redundant resource clearance, all registries has been taken care of.");
        return DEFAULT_NOP_REGISTRY;
    }

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
        //create registry by spi/ioc
        registry = createRegistry(url);
        if (registry == null) {
            throw new IllegalStateException("Can not create registry " + url);
        }
        REGISTRIES.put(key, registry);
        //registr={zookeeper://192.168.56.101:2181/org.apache.dubbo.registry.RegistryService?application=dubbo-consumer&dubbo=2.0.2&interface=org.apache.dubbo.registry.RegistryService&pid=1580&qos.enable=false&release=2.7.5&timeout=10000&timestamp=1599059110313}
        return registry;
    } finally {
        // Release the lock
        LOCK.unlock();
    }
}

```

获取registry对象后，最后又执行doRefer方法；

`org.apache.dubbo.registry.integration.RegistryProtocol#doRefer`

```java
private <T> Invoker<T> doRefer(Cluster cluster, Registry registry, Class<T> type, URL url) {
    RegistryDirectory<T> directory = new RegistryDirectory<T>(type, url);
    directory.setRegistry(registry);
    directory.setProtocol(protocol);
    // all attributes of REFER_KEY
    Map<String, String> parameters = new HashMap<String, String>(directory.getUrl().getParameters());
    URL subscribeUrl = new URL(CONSUMER_PROTOCOL, parameters.remove(REGISTER_IP_KEY), 0, type.getName(), parameters);
    // 注册服务消费者，在 consumers 目录下新节点
    if (!ANY_VALUE.equals(url.getServiceInterface()) && url.getParameter(REGISTER_KEY, true)) {
        directory.setRegisteredConsumerUrl(getRegisteredConsumerUrl(subscribeUrl, url));
        registry.register(directory.getRegisteredConsumerUrl());
    }
    directory.buildRouterChain(subscribeUrl);
     // 订阅 providers、configurators、routers 等节点数据
    directory.subscribe(subscribeUrl.addParameter(CATEGORY_KEY,
                                                  PROVIDERS_CATEGORY + "," + CONFIGURATORS_CATEGORY + "," + ROUTERS_CATEGORY));
	// 一个注册中心可能有多个服务提供者，因此这里需要将多个服务提供者合并为一个
    Invoker invoker = cluster.join(directory);
    return invoker;
}
```

> ​	如上，doRefer 方法创建一个 RegistryDirectory 实例，然后生成服务者消费者链接，并向注册中心进行注册。注册完毕后，紧接着订阅 providers、configurators、routers 等节点下的数据。完成订阅后，RegistryDirectory 会收到这几个节点下的子节点信息。由于一个服务可能部署在多台服务器上，这样就会在 providers 产生多个节点，这个时候就需要 Cluster 将多个服务节点合并为一个，并生成一个 Invoker。

`org.apache.dubbo.rpc.proxy.AbstractProxyFactory#getProxy(org.apache.dubbo.rpc.Invoker<T>)`:创建代理

```java
@Override
public <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException {
    Set<Class<?>> interfaces = new HashSet<>();

    String config = invoker.getUrl().getParameter(INTERFACES);
    if (config != null && config.length() > 0) {
        String[] types = COMMA_SPLIT_PATTERN.split(config);
        if (types != null && types.length > 0) {
            for (int i = 0; i < types.length; i++) {
                // TODO can we load successfully for a different classloader?.
                interfaces.add(ReflectUtils.forName(types[i]));
            }
        }
    }

    if (!GenericService.class.isAssignableFrom(invoker.getInterface()) && generic) {
        interfaces.add(com.alibaba.dubbo.rpc.service.GenericService.class);
    }

    interfaces.add(invoker.getInterface());
    interfaces.addAll(Arrays.asList(INTERNAL_INTERFACES));

    return getProxy(invoker, interfaces.toArray(new Class<?>[0]));
}

```

`org.apache.dubbo.rpc.proxy.javassist.JavassistProxyFactory#getProxy`

```java
@Override
public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
    return (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
}
```


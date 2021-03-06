# `dubbo`

## `dubbo`核心`SPI`

​	可以添加自定义扩展，以满足不同的需求。例如`dubbo`中协议满足不了需求，则可以自己扩展实现`Protocol`接口。

### 1、`dubbo`扩展点与`jdk spi`机制对比

|         | 加载时机                                                     | `IOC和APP支持`                                     |
| ------- | ------------------------------------------------------------ | -------------------------------------------------- |
| `dubbo` | `Dubbo SPI` 是按需加载，只加载需要使用的实现类。同时带有缓存支持。 | 支持，并且一个扩展点可以直接`setter`注入其它扩展点 |
| `jdk`   | 一次性实例化扩展点所有实现（加载速度缓慢）                   | 否                                                 |

### 2`Quick Start`

- 引入jar包

```xml
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>2.7.5</version>
</dependency>
```

- 定义接口和实现类

```java
@SPI("dubbo")
public interface Protocol {
    int getDefaultPort();
}
```

```java
public class MyProtocol implements Protocol {
    @Override
    public int getDefaultPort() {
        return 9999;
    }
	...
}
```

- 创建扩展文件:`org.apache.dubbo.rpc.Protocol`

```text
myProtocol=com.fuzy.example.protocol.MyProtocol
```

- 启动类

```java
public class ProtocolBootstrap {
    public static void main(String[] args) {
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol myProtocol = extensionLoader.getExtension("myProtocol");
        System.out.println(myProtocol.getDefaultPort());
    }
}
```

`ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class)`流程如下：

![dubbo获取加载器](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20200730223517.jpg)

### 3`源码解析-getExtensionLoader`

`org.apache.dubbo.common.extension.ExtensionLoader#getExtensionLoader`:获取类加载器

```java
public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
    ...
    ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
    if (loader == null) {
 		//new 实例化对象
        EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
        loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
    }
    return loader;
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#ExtensionLoader`:初始化`ExtensionLoader`实例化

```java
//以MyProtocol为例，此时type=Protocol.class，objectFactory=AdaptiveExtensionFactory
private ExtensionLoader(Class<?> type) {
    this.type = type;
    //ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension()初始化ExtensionFactory
    objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#getAdaptiveExtension`:得到自适应扩展点

```java
public T getAdaptiveExtension() {
    //private final Holder<Object> cachedAdaptiveInstance = new Holder<>();
    Object instance = cachedAdaptiveInstance.get();
    // 缓存未命中
    if (instance == null) {
        	...
            synchronized (cachedAdaptiveInstance) {
            instance = cachedAdaptiveInstance.get();
            if (instance == null) {
                ...省略try catch
                // 创建自适应拓展
                //本例中instance=AdaptiveExtensionFactory
                instance = createAdaptiveExtension();
                // 设置自适应拓展到缓存中
                cachedAdaptiveInstance.set(instance); 
            }
        }
    }
```

`org.apache.dubbo.common.extension.ExtensionLoader#createAdaptiveExtension`：反射实例化并依赖注入

```java
/**
 *1、调用 getAdaptiveExtensionClass 方法获取自适应拓展 Class 对象
 *2、通过反射进行实例化
 *3、调用 injectExtension 方法向拓展实例中注入依赖
 */
private T createAdaptiveExtension() {
	...省略部分代码	
    // 获取自适应拓展类，并通过反射实例化
    //Dubbo IOC 目前仅支持 setter 方式注入
    return injectExtension((T) getAdaptiveExtensionClass().newInstance());
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#getAdaptiveExtensionClass`：获取自适应扩展类

```java
private Class<?> getAdaptiveExtensionClass() {
    getExtensionClasses();
    if (cachedAdaptiveClass != null) {
        //返回的是带有@Adapative注解的类
        return cachedAdaptiveClass;
    }
    //当扫描的文件所有类上不带有@Adapative注解时，会执行此方法生成代理类代码。后续介绍
    return cachedAdaptiveClass = createAdaptiveExtensionClass();
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#getExtensionClasses`：为缓存`cachedClasses`属性赋值

```java
private Map<String, Class<?>> getExtensionClasses() {
    //private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    Map<String, Class<?>> classes = cachedClasses.get();
    if (classes == null) {
        synchronized (cachedClasses) {
            classes = cachedClasses.get();
            if (classes == null) {
                //cachedClasses存储SpringExtensionFactory和SpiExtensionFactory
                classes = loadExtensionClasses();
                cachedClasses.set(classes);
            }
        }
    }
    return classes;
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#loadExtensionClasses`:从指定的目录下加载文件

`org.apache.dubbo.common.extension.ExtensionFactory`配置文件

```properties
spring=org.apache.dubbo.config.spring.extension.SpringExtensionFactory
#AdaptiveExtensionFactory带有@Adaptive注解
adaptive=org.apache.dubbo.common.extension.factory.AdaptiveExtensionFactory
spi=org.apache.dubbo.common.extension.factory.SpiExtensionFactory
```

```java
private Map<String, Class<?>> loadExtensionClasses() {
    //为cachedDefaultName赋值，该值是@Spi(value='')中的值，匹配配置文件中的key
    cacheDefaultExtensionName();
	
    Map<String, Class<?>> extensionClasses = new HashMap<>();
    // internal extension load from ExtensionLoader's ClassLoader first
    //type=ExtensionFactory.class
    loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName(), true);
    loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"), true);

    loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName());
    loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
    loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName());
    loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
    return extensionClasses;
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#loadResource`:通过字符流读取配置文件

```java
private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, java.net.URL resourceURL) {
    try {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final int ci = line.indexOf('#');
                if (ci >= 0) {
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        String name = null;
                        int i = line.indexOf('=');
                        if (i > 0) {
                            name = line.substring(0, i).trim();
                            line = line.substring(i + 1).trim();
                        }
                        if (line.length() > 0) {
                            loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
                        }
                    } catch (Throwable t) {
                        ...
                    }
                }
            }
        }
    } catch (Throwable t) {
        ...
    }
}
```

`org.apache.dubbo.common.extension.ExtensionLoader#loadClass`：将加载的文件赋值给对应的属性

```java
//从配置文件加载出来的分别是三个类SpringExtensionFactory、AdaptiveExtensionFactory、SpiExtensionFactory
private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name) throws NoSuchMethodException {
        ...
        //缓存带有注解的类 AdaptiveExtensionFactory 
        if (clazz.isAnnotationPresent(Adaptive.class)) {
            //给cachedAdaptiveClass赋值
            cacheAdaptiveClass(clazz);
        } else if (isWrapperClass(clazz)) {
            //缓存包装类ProtocolFilterWrapper、ProtocolListenerWrapper
            cacheWrapperClass(clazz);
        } else {
            //其他类非包装类并且类上不带有@Adaptive注解的添加到cacheActivateClass
            clazz.getConstructor();
            if (StringUtils.isEmpty(name)) {
                name = findAnnotationName(clazz);
                ...
            }	

            String[] names = NAME_SEPARATOR.split(name);
            if (ArrayUtils.isNotEmpty(names)) {
                cacheActivateClass(clazz, names[0]);
                for (String n : names) {
                    cacheName(clazz, n);
                    saveInExtensionClass(extensionClasses, clazz, n);
                }
            }
        }
    }
```

`org.apache.dubbo.common.extension.ExtensionLoader#injectExtension`:为实例化的类注入依赖

```java
//此时传入的T是AdaptiveExtensionFactory实例
private T injectExtension(T instance) {
    ...省略try catch
    for (Method method : instance.getClass().getMethods()) {
        //是否set方法
        if (!isSetter(method)) {
            continue;
        }
        //是否包含注解DisableInject
        if (method.getAnnotation(DisableInject.class) != null) {
            continue;
        }
        Class<?> pt = method.getParameterTypes()[0];
        if (ReflectUtils.isPrimitives(pt)) {
            continue;
        }

        try {
            String property = getSetterProperty(method);
            //objectFactory= AdaptiveExtensionFactory
            Object object = objectFactory.getExtension(pt, property);
            if (object != null) {
                //实例化
                method.invoke(instance, object);
            }
        } catch (Exception e) {
            logger.error("Failed to inject via method " + method.getName()
                         + " of interface " + type.getName() + ": " + e.getMessage(), e);
        }
    }
    return instance;
}
```

​	上述代码会传入`AdaptiveExtensionFactory`实例，然后用`for`循环依次执行该实例的方法，该实例代码如下所示：

```java
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {
    private final List<ExtensionFactory> factories;
    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        for (String name : loader.getSupportedExtensions()) {
            //分别会调用SpiExtensionLoader 和SpringExtensionLoader
            list.add(loader.getExtension(name));
        }
        //初始化factories ={SpringExtensionFactory，SpiExtensionFactory}
        factories = Collections.unmodifiableList(list);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
```

> 小结：在获取**指定名称的扩展点**过程中，通过`getExtensionLoader()`整个调用流程初始化`ExtensionLoader`对象中`objectFactory`属性。在初始化过程中主要做了以下事情：
>
> - `cachedAdaptiveClass`存储`@Adaptive`注解的类  例如：`AdaptiveExtensionFactory`
> - `cachedWrapperClasses`存储构造方法带有扩展点参数的包装类  例如：`ProtocolFilterWrapper、ProtocolListenerWrapper`
> - `cachedActivates`存储带有`@Activate`注解的类
> - `cachedClasses`存储实现了扩展点的类  例如：`SpiExtensionFactory、SpringExtensionFactory`

`Protocol myProtocol = extensionLoader.getExtension("myProtocol")`执行流程：

### 4  案例

**以下案例均来源于`dubbo`源码中`common`模块中的`ExtensionLoader_Adaptive_Test`类中**

#### 4.1  指定名称的扩展点

```java
@Test
public void test_staticAdaptiveClass()throws Exception{
    //与Quick Start中逻辑一样
    HasAdaptiveExt impl1 = ExtensionLoader.getExtensionLoader(HasAdaptiveExt.class).getExtension("impl1");
    assertTrue(impl1 instanceof HasAdaptiveExtImpl1);
}
```

**源码分析**

`org.apache.dubbo.common.extension.ExtensionLoader#getExtension`

```java
public T getExtension(String name) {
       ...
        final Holder<Object> holder = getOrCreateHolder(name);
        Object instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    //根据指定名称获取对应的实体类
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }
```

`org.apache.dubbo.common.extension.ExtensionLoader#createExtension`

```java
/**
 *   1、通过 getExtensionClasses 获取所有的拓展类
 *   2、通过反射创建拓展对象
 *   3、向拓展对象中注入依赖
 *   4、将拓展对象包裹在相应的 Wrapper 对象中
 */
private T createExtension(String name) {
    // 从配置文件中加载所有的拓展类，可得到“配置项名称”到“配置类”的映射关系表
    Class<?> clazz = getExtensionClasses().get(name);
    if (clazz == null) {
        throw findException(name);
    }
    try {
        T instance = (T) EXTENSION_INSTANCES.get(clazz);
        if (instance == null) {
            EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
            instance = (T) EXTENSION_INSTANCES.get(clazz);
        }
        // 向实例中注入依赖
        injectExtension(instance);
        Set<Class<?>> wrapperClasses = cachedWrapperClasses;
        if (CollectionUtils.isNotEmpty(wrapperClasses)) {
            // 循环创建 Wrapper 实例
            for (Class<?> wrapperClass : wrapperClasses) {
                // 将当前 instance 作为参数传给 Wrapper 的构造方法，并通过反射创建 Wrapper 实例。
                // 然后向 Wrapper 实例中注入依赖，最后将 Wrapper 实例再次赋值给 instance 变量
                instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
            }
        }
        initExtension(instance);
        return instance;
    } catch (Throwable t) {
        throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
                                        type + ") couldn't be instantiated: " + t.getMessage(), t);
    }
}
```

#### 4.2 自适应扩展点

##### 4.2.1、类上加@Adaptive注解

```java
/**
 * 因为HasAdaptiveExt_ManualAdaptive类上加了@Adaptive注解，
 * 当调用getAdaptiveExtension()方法时，会给cachedAdaptiveInstance赋值，获得HasAdaptiveExt_ManualAdaptive实例
 * 具体赋值方法请参考org.apache.dubbo.common.extension.ExtensionLoader#loadClass
 */
@Test
public void test_useAdaptiveClass() throws Exception {
    ExtensionLoader<HasAdaptiveExt> loader = ExtensionLoader.getExtensionLoader(HasAdaptiveExt.class);
    HasAdaptiveExt ext = loader.getAdaptiveExtension();
    assertTrue(ext instanceof HasAdaptiveExt_ManualAdaptive);
}
```

**源码解析**

```java
public T getAdaptiveExtension() {
    Object instance = cachedAdaptiveInstance.get();
    if (instance == null) {
        if (createAdaptiveInstanceError == null) {
            synchronized (cachedAdaptiveInstance) {
                instance = cachedAdaptiveInstance.get();
                if (instance == null) {
                    try {
                        //创建自适应 加载带有@Adaptive的类
                        instance = createAdaptiveExtension();
                        cachedAdaptiveInstance.set(instance);
                    } catch (Throwable t) {
                        createAdaptiveInstanceError = t;
                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
                    }
                }
            }
        } else {
            throw new IllegalStateException("Failed to create adaptive instance: " + createAdaptiveInstanceError.toString(), createAdaptiveInstanceError);
        }
    }

    return (T) instance;
}
```

##### 4.2.2  方法上加@Adaptive注解

**配置文件**

```properties
impl1=org.apache.dubbo.common.extension.ext1.impl.SimpleExtImpl1
impl2=org.apache.dubbo.common.extension.ext1.impl.SimpleExtImpl2  
impl3=org.apache.dubbo.common.extension.ext1.impl.SimpleExtImpl3 
```

**接口类**

```java
/**
 * Simple extension, has no wrapper
 * @SPI 中赋值，代表默认加载的类映射配置文件中的key
 */
@SPI("impl1")
public interface SimpleExt {
    @Adaptive
    String echo(URL url, String s);
}
```

**测试类**

```java
/**
 * url驱动,扫描的配置文件所有类上没有@Adaptive注解，会执行	org.apache.dubbo.common.extension.ExtensionLoader#createAdaptiveExtensionClass该方法，生成对应的代理类，所以ext是生成的代理类。
 */
@Test
public void test_getAdaptiveExtension_defaultAdaptiveKey() throws Exception {
    {
        SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class).getAdaptiveExtension();
        //map为空时，默认加载接口上@Spi指定的value impl1
        Map<String, String> map = new HashMap<String, String>();
        URL url = new URL("p1", "1.2.3.4", 1010, "path1", map);
        String echo = ext.echo(url, "haha");
        assertEquals("Ext1Impl1-echo", echo);
    }

    {
        SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class).getAdaptiveExtension();

        Map<String, String> map = new HashMap<String, String>();
        //通过传参设置要调用的实现类
        map.put("simple.ext", "impl2");
        URL url = new URL("p1", "1.2.3.4", 1010, "path1", map);
        //map初始化值则设置，否则就取接口上@Spi注解中value对应的属性值
        String extName = url.getParameter("simple.ext", "impl1");
        String echo = ext.echo(url, "haha");
        assertEquals("Ext1Impl2-echo", echo);
    }
}
```

**生成的代理类**

```java
package org.apache.dubbo.common.extension;
public class SimpleExt$Adaptive implements org.apache.dubbo.common.extension.ext1.SimpleExt {
   public java.lang.String echo(org.apache.dubbo.common.URL arg0, java.lang.String arg1)  {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
       //如果url没有给map初始化值，则会默认接口@Spi中value的值匹配配置文件中的key值
        String extName = url.getParameter("simple.ext", "impl1");
        if(extName == null) throw new IllegalStateException("Failed to get extension (org.apache.dubbo.common.extension.ext1.SimpleExt) name from url (" + url.toString() + ") use keys([simple.ext])");
       //根据extName执行指定名称的扩展点逻辑
        org.apache.dubbo.common.extension.ext1.SimpleExt extension = (org.apache.dubbo.common.extension.ext1.SimpleExt)ExtensionLoader.getExtensionLoader(org.apache.dubbo.common.extension.ext1.SimpleExt.class).getExtension(extName);
        return extension.echo(arg0, arg1);
    }
}
```

**源码解析**

​	**当扫描的带有注解`@Spi`的接口实现类没有`@Adaptive`注解时，会通过`createAdaptiveExtensionClass`此方法生成代理类。**

`org.apache.dubbo.common.extension.ExtensionLoader#createAdaptiveExtensionClass`:动态生成代理类

```java	
private Class<?> createAdaptiveExtensionClass() {
    //生成动态代理类代码
    String code = new AdaptiveClassCodeGenerator(type, cachedDefaultName).generate();
    //获取类加载器
    ClassLoader classLoader = findClassLoader();
    //默认javassist编译
    org.apache.dubbo.common.compiler.Compiler compiler = ExtensionLoader.getExtensionLoader(org.apache.dubbo.common.compiler.Compiler.class).getAdaptiveExtension();
    //编译
    return compiler.compile(code, classLoader);
}
```

#### 4.3  激活扩展点

​	自动激活扩展点，有点类似我们讲`springboot`的时候用到的`conditional`，根据条件进行自动激活。但是这里设计的初衷是，对 于一个类会加载多个扩展点的实现，这个时候可以通过自动激活扩展点进行动态加载。以下代码来自于`dubbo`源码中的`common`模块下的`ExtensionLoaderTest`类中

```java
@Test
public void testLoadActivateExtension() throws Exception {
    // test default
    URL url = URL.valueOf("test://localhost/test");
    List<ActivateExt1> list = ExtensionLoader.getExtensionLoader(ActivateExt1.class)
        .getActivateExtension(url, new String[]{}, "default_group");
    Assertions.assertEquals(1, list.size());
    Assertions.assertSame(list.get(0).getClass(), ActivateExt1Impl1.class);

    // test group
    url = url.addParameter(GROUP_KEY, "group1");
    list = ExtensionLoader.getExtensionLoader(ActivateExt1.class)
        .getActivateExtension(url, new String[]{}, "group1");
    Assertions.assertEquals(1, list.size());
    Assertions.assertSame(list.get(0).getClass(), GroupActivateExtImpl.class);

    // test old @Activate group
    url = url.addParameter(GROUP_KEY, "old_group");
    list = ExtensionLoader.getExtensionLoader(ActivateExt1.class)
        .getActivateExtension(url, new String[]{}, "old_group");
    Assertions.assertEquals(2, list.size());
    Assertions.assertTrue(list.get(0).getClass() == OldActivateExt1Impl2.class
                          || list.get(0).getClass() == OldActivateExt1Impl3.class);

    // test value
    url = url.removeParameter(GROUP_KEY);
    url = url.addParameter(GROUP_KEY, "value");
    url = url.addParameter("value", "value");
    list = ExtensionLoader.getExtensionLoader(ActivateExt1.class)
        .getActivateExtension(url, new String[]{}, "value");
    Assertions.assertEquals(1, list.size());
    Assertions.assertSame(list.get(0).getClass(), ValueActivateExtImpl.class);

    // test order
    url = URL.valueOf("test://localhost/test");
    url = url.addParameter(GROUP_KEY, "order");
    list = ExtensionLoader.getExtensionLoader(ActivateExt1.class)
        .getActivateExtension(url, new String[]{}, "order");
    Assertions.assertEquals(2, list.size());
    Assertions.assertSame(list.get(0).getClass(), OrderActivateExtImpl1.class);
    Assertions.assertSame(list.get(1).getClass(), OrderActivateExtImpl2.class);
}
```

**源码分析**

`org.apache.dubbo.common.extension.ExtensionLoader#getActivateExtension`

```java
public List<T> getActivateExtension(URL url, String[] values, String group) {
    List<T> exts = new ArrayList<>();
    List<String> names = values == null ? new ArrayList<>(0) : Arrays.asList(values);
    if (!names.contains(REMOVE_VALUE_PREFIX + DEFAULT_KEY)) {
        getExtensionClasses();
        for (Map.Entry<String, Object> entry : cachedActivates.entrySet()) {
            String name = entry.getKey();
            Object activate = entry.getValue();

            String[] activateGroup, activateValue;

            if (activate instanceof Activate) {
                activateGroup = ((Activate) activate).group();
                activateValue = ((Activate) activate).value();
            } else if (activate instanceof com.alibaba.dubbo.common.extension.Activate) {
                activateGroup = ((com.alibaba.dubbo.common.extension.Activate) activate).group();
                activateValue = ((com.alibaba.dubbo.common.extension.Activate) activate).value();
            } else {
                continue;
            }
            if (isMatchGroup(group, activateGroup)) {
                T ext = getExtension(name);
                if (!names.contains(name)
                    && !names.contains(REMOVE_VALUE_PREFIX + name)
                    && isActive(activateValue, url)) {
                    //将激活扩展点加入的集合里面
                    exts.add(ext);
                }
            }
        }
        //排序
        exts.sort(ActivateComparator.COMPARATOR);
    }
    List<T> usrs = new ArrayList<>();
    for (int i = 0; i < names.size(); i++) {
        String name = names.get(i);
        if (!name.startsWith(REMOVE_VALUE_PREFIX)
            && !names.contains(REMOVE_VALUE_PREFIX + name)) {
            if (DEFAULT_KEY.equals(name)) {
                if (!usrs.isEmpty()) {
                    exts.addAll(0, usrs);
                    usrs.clear();
                }
            } else {
                T ext = getExtension(name);
                usrs.add(ext);
            }
        }
    }
    if (!usrs.isEmpty()) {
        exts.addAll(usrs);
    }
    return exts;
}
```






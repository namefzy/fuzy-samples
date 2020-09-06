# SpringBoot源码解析-Jar启动实现

## 1、结构概述

`Spring Boot jar`包结构，如下图：

![jar包](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20200901204101.png)

## 2、MANIFEST.MF文件

- ① `META-INF` 目录：通过 `MANIFEST.MF` 文件提供 `jar` 包的**元数据**，声明了 `jar` 的启动类。

```makefile
Manifest-Version: 1.0
#引用jar包地址
Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
Implementation-Title: spring-boot
Implementation-Version: 0.0.1-SNAPSHOT
#Spring规定的主启动类
Start-Class: com.fuzy.example.springboot.Application
Spring-Boot-Classes: BOOT-INF/classes/
Spring-Boot-Lib: BOOT-INF/lib/
Build-Jdk-Spec: 1.8
Spring-Boot-Version: 2.3.3.RELEASE
Created-By: Maven Jar Plugin 3.2.0
#java规定jar包的启动类
Main-Class: org.springframework.boot.loader.JarLauncher
```

- ② `org` 目录：为 Spring Boot 提供的 [`spring-boot-loader`](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/) 项目，它是 `java -jar` 启动 Spring Boot 项目的秘密所在，也是稍后我们将深入了解的部分。

那么我们为什么不直接运行`Application`类来启动项目呢？执行启动Application命令：

```shell
$ java -classpath lab-39-demo-2.2.2.RELEASE.jar com.fuzy.example.springboot.Application
错误: 找不到或无法加载主类 com.fuzy.example.springboot.Application
```

产生上述错误的原因有两个：

- `Application`在`BOOT-INF/classes`目录下，不符合`jar`包的规则
- `Java`规定可执行器的`jar`包禁止嵌套其他`jar`。但是我们可以看到 `BOOT-INF/lib` 目录下，实际有 Spring Boot 应用依赖的所有 `jar` 包。因此，`spring-boot-loader` 项目自定义实现了 ClassLoader 实现类 [LaunchedURLClassLoader](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/LaunchedURLClassLoader.java)，支持加载 `BOOT-INF/classes` 目录下的 `.class` 文件，以及 `BOOT-INF/lib` 目录下的 `jar` 包。

## 3、JarLauncher

```java
public class JarLauncher extends ExecutableArchiveLauncher {

	static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";

	static final String BOOT_INF_LIB = "BOOT-INF/lib/";

	public JarLauncher() {
	}

	protected JarLauncher(Archive archive) {
		super(archive);
	}

	@Override
	protected boolean isNestedArchive(Archive.Entry entry) {
		if (entry.isDirectory()) {
			return entry.getName().equals(BOOT_INF_CLASSES);
		}
		return entry.getName().startsWith(BOOT_INF_LIB);
	}
    //main是启动方法
	public static void main(String[] args) throws Exception {
        //调用父类的方法
		new JarLauncher().launch(args);
	}

}
```

`org.springframework.boot.loader.Launcher#launch(java.lang.String[])`：启动应用程序

```java
protected void launch(String[] args) throws Exception {
    // <1> 注册 URL 协议的处理器
	JarFile.registerUrlProtocolHandler();
	// <2> 创建类加载器
	ClassLoader classLoader = createClassLoader(getClassPathArchives());
	// <3> 执行启动类的 main 方法
	launch(args, getMainClass(), classLoader);
}
```

- <1>处注册`Spring Boot`自定义的[URLStreamHandler](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/net/URLStreamHandler.java) 实现类，用于 `jar` 包的加载读取。
- <2>处调用自身的 `#createClassLoader(List archives)` 方法，创建自定义的 [ClassLoader](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/lang/ClassLoader.java) 实现类，用于从 `jar` 包中加载类。
- `<3>` 处，执行我们声明的 Spring Boot 启动类，进行 Spring Boot 应用的启动。

​      简单来说，就是整一个可以读取 `jar` 包中类的加载器，保证 `BOOT-INF/lib` 目录下的类和 `BOOT-classes` 内嵌的 `jar` 中的类能够被正常加载到，之后执行 Spring Boot 应用的启动。

### 3.1 ` registerUrlProtocolHandler`

```java
private static final String PROTOCOL_HANDLER = "java.protocol.handler.pkgs";

private static final String HANDLERS_PACKAGE = "org.springframework.boot.loader";

public static void registerUrlProtocolHandler() {
    // 获得 URLStreamHandler 的路径
	String handlers = System.getProperty(PROTOCOL_HANDLER, "");
	// 将 Spring Boot 自定义的 HANDLERS_PACKAGE(org.springframework.boot.loader) 补充上去
	System.setProperty(PROTOCOL_HANDLER, ("".equals(handlers) ? HANDLERS_PACKAGE
			: handlers + "|" + HANDLERS_PACKAGE));
	// 重置已缓存的 URLStreamHandler 处理器们
	resetCachedUrlHandlers();
}

/**
 * 重置 URL 中的 URLStreamHandler 的缓存，防止 `jar://` 协议对应的 URLStreamHandler 已经创建
 * 我们通过设置 URLStreamHandlerFactory 为 null 的方式，清空 URL 中的该缓存。
 */
private static void resetCachedUrlHandlers() {
	try {
		URL.setURLStreamHandlerFactory(null);
	} catch (Error ex) {
		// Ignore
	}
}
```

### 3.2 `createClassLoader`

#### 3.2.1  `getClassPathArchives()`

`org.springframework.boot.loader.ExecutableArchiveLauncher#getClassPathArchives`

```java

@Override
protected List<Archive> getClassPathArchives() throws Exception {
    List<Archive> archives = new ArrayList<>(this.archive.getNestedArchives(this::isNestedArchive));
    postProcessClassPathArchives(archives);
    return archives;
}

protected abstract boolean isNestedArchive(Archive.Entry entry);

protected void postProcessClassPathArchives(List<Archive> archives) throws Exception {
}
```

`org.springframework.boot.loader.JarLauncher#isNestedArchive`：获得`BOOT-INF/classes/` 目录下的类，以及 `BOOT-INF/lib/` 的内嵌 `jar` 包

```java
static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";

static final String BOOT_INF_LIB = "BOOT-INF/lib/";

@Override
protected boolean isNestedArchive(Archive.Entry entry) {
    // 如果是目录的情况，只要 BOOT-INF/classes/ 目录
	if (entry.isDirectory()) {
		return entry.getName().equals(BOOT_INF_CLASSES);
	}
	// 如果是文件的情况，只要 BOOT-INF/lib/ 目录下的 `jar` 包
	return entry.getName().startsWith(BOOT_INF_LIB);
}
```

`org.springframework.boot.loader.Launcher#createArchive`:

```java
//ExecutableArchiveLauncher构造方法
public ExecutableArchiveLauncher() {
    try {
        //给archive赋值
        this.archive = createArchive();
    }
    catch (Exception ex) {
        throw new IllegalStateException(ex);
    }
}
//launcher 方法 决定到底是哪个类调用getNestedArchives方法
protected final Archive createArchive() throws Exception {
     // 获得 jar 所在的绝对路径
    ProtectionDomain protectionDomain = getClass().getProtectionDomain();
    CodeSource codeSource = protectionDomain.getCodeSource();
    URI location = (codeSource != null) ? codeSource.getLocation().toURI() : null;
    String path = (location != null) ? location.getSchemeSpecificPart() : null;
    if (path == null) {
        throw new IllegalStateException("Unable to determine code source archive");
    }
    File root = new File(path);
    if (!root.exists()) {
        throw new IllegalStateException("Unable to determine code source archive from " + root);
    }
    // 如果是目录，则使用 ExplodedArchive 进行展开
    // 如果不是目录，则使用 JarFileArchive
    //对于jar包来说root为jar包的绝对路径
    return (root.isDirectory() ? new ExplodedArchive(root) : new JarFileArchive(root));
}
```

![14](https://image-1301573777.cos.ap-chengdu.myqcloud.com/20200901230152.png)

- ExplodedArchive 是针对**目录**的 Archive 实现类。
- JarFileArchive 是针对 **`jar` 包**的 Archive 实现类。

`org.springframework.boot.loader.archive.JarFileArchive#getNestedArchives`:获得 `archive` 内嵌的 Archive 集合

> ```java
> jar:file:.../BOOT-INF/classes!/
> jar:file:.../BOOT-INF/lib/spring-boot-starter-web-2.2.2.RELEASE.jar!/
> jar:file:.../BOOT-INF/lib/spring-boot-starter-2.2.2.RELEASE.jar!/
> jar:file:.../BOOT-INF/lib/spring-boot-2.2.2.RELEASE.jar!/
> ```
>
> ​	BOOT-INF/classes/ 目录被归类为一个 Archive 对象，而 BOOT-INF/lib/ 目录下的每个内嵌 jar 包都对应一个 Archive 对象。

```java
@Override
public List<Archive> getNestedArchives(EntryFilter filter) throws IOException {
    List<Archive> nestedArchives = new ArrayList<>();
    for (Entry entry : this) {
        if (filter.matches(entry)) {
            nestedArchives.add(getNestedArchive(entry));
        }
    }
    return Collections.unmodifiableList(nestedArchives);
}
```

#### 3.2.2 `createClassLoader()`

`org.springframework.boot.loader.Launcher#createClassLoader(java.util.List<org.springframework.boot.loader.archive.Archive>)`

```java
protected ClassLoader createClassLoader(List<Archive> archives) throws Exception {
    List<URL> urls = new ArrayList<>(archives.size());
    for (Archive archive : archives) {
        urls.add(archive.getUrl());
    }
    // 创建加载这些 URL 的 ClassLoader
    return createClassLoader(urls.toArray(new URL[0]));
}

protected ClassLoader createClassLoader(URL[] urls) throws Exception {
    return new LaunchedURLClassLoader(urls, getClass().getClassLoader());
}

protected void launch(String[] args, String mainClass, ClassLoader classLoader) throws Exception {
    Thread.currentThread().setContextClassLoader(classLoader);
    createMainMethodRunner(mainClass, args, classLoader).run();
}

```

​	基于获得的 Archive 数组，创建自定义 ClassLoader 实现类 [LaunchedURLClassLoader](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/LaunchedURLClassLoader.java)，通过它来加载 `BOOT-INF/classes` 目录下的类，以及 `BOOT-INF/lib` 目录下的 `jar` 包中的类。

### 3.3  launch()

`launch(args, getMainClass(), classLoader)`方法：启动应用的方法

#### 3.3.1 getMainClass()

`org.springframework.boot.loader.ExecutableArchiveLauncher#getMainClass`:获得启动主类

```java
@Override
protected String getMainClass() throws Exception {
    //获取Start-Class对应类
    Manifest manifest = this.archive.getManifest();
    String mainClass = null;
    if (manifest != null) {
        mainClass = manifest.getMainAttributes().getValue("Start-Class");
    }
    if (mainClass == null) {
        throw new IllegalStateException("No 'Start-Class' manifest entry specified in " + this);
    }
    return mainClass;
}
```

#### 3.3.2 launch()

`org.springframework.boot.loader.Launcher#launch(java.lang.String[], java.lang.String, java.lang.ClassLoader)`:启动应用

```java
protected void launch(String[] args, String mainClass, ClassLoader classLoader) throws Exception {
    //当前线程设置类加载器
    Thread.currentThread().setContextClassLoader(classLoader);
    //启动SpringBoot应用
    createMainMethodRunner(mainClass, args, classLoader).run();
}

protected MainMethodRunner createMainMethodRunner(String mainClass, String[] args, ClassLoader classLoader) {
    return new MainMethodRunner(mainClass, args);
}
```

`MainMethodRunner类`:`获得MANIFEST.MF`中`Start-Class`对应的类，调用`run`方法，执行`SpringBoot`加载逻辑

```java
public class MainMethodRunner {

    private final String mainClassName;

    private final String[] args;

    /**
	 * Create a new {@link MainMethodRunner} instance.
	 * @param mainClass the main class
	 * @param args incoming arguments
	 */
    public MainMethodRunner(String mainClass, String[] args) {
        this.mainClassName = mainClass;
        this.args = (args != null) ? args.clone() : null;
    }

    public void run() throws Exception {
        //通过 LaunchedURLClassLoader 类加载器，加载到我们设置的 Spring Boot 的主启动类。
        Class<?> mainClass = Thread.currentThread().getContextClassLoader().loadClass(this.mainClassName);
        //通过反射调用主启动类的 #main(String[] args) 方法
        Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, new Object[] { this.args });
    }

}
```

## 4、`LaunchedURLClassLoader`类

[	LaunchedURLClassLoader](https://github.com/DarLiner/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/LaunchedURLClassLoader.java) 是 `spring-boot-loader` 项目自定义的**类加载器**，实现对 `jar` 包中 `META-INF/classes` 目录下的**类**和 `META-INF/lib` 内嵌的 `jar` 包中的**类**的**加载**。

> FROM [《维基百科 —— Java 类加载器》](https://zh.wikipedia.org/wiki/Java类加载器)
>
> ​	Java 类加载器是 Java 运行时环境的一个部件，负责动态加载 Java 类到 Java 虚拟机的内存空间中。类通常是按需加载，即第一次使用该类时才加载。由于有了类加载器，Java 运行时系统不需要知道文件与文件系统。对学习类加载器而言，掌握 Java 的委派概念是很重要的。每个 Java 类必须由某个类加载器装入到内存。

回到之前的代码：

```java
protected ClassLoader createClassLoader(List<Archive> archives) throws Exception {
    // 获得所有 Archive 的 URL 地址
    List<URL> urls = new ArrayList<>(archives.size());
    for (Archive archive : archives) {
        urls.add(archive.getUrl());
    }
    // 创建加载这些 URL 的 ClassLoader
    return createClassLoader(urls.toArray(new URL[0]));
}

protected ClassLoader createClassLoader(URL[] urls) throws Exception {
	return new LaunchedURLClassLoader(urls, getClass().getClassLoader());
}

// LaunchedURLClassLoader.java

public class LaunchedURLClassLoader extends URLClassLoader {

	public LaunchedURLClassLoader(URL[] urls, ClassLoader parent) {
        //urls，使用的是 Archive 集合对应的 URL 地址们，从而告诉 LaunchedURLClassLoader 读取 jar 的地址。
        //第二个参数 parent，设置 LaunchedURLClassLoader 的父加载器。这里后续胖友可以理解下，类加载器的双亲委派模型
		super(urls, parent);
	}
	
}	
```

## 5、总结

总体来说，Spring Boot `jar` 启动的原理是非常清晰的，整体如下图所示：

[![Spring Boot  启动原理](http://www.iocoder.cn/images/Spring-Boot/2019-01-07/30.png)](http://www.iocoder.cn/images/Spring-Boot/2019-01-07/30.png)Spring Boot 启动原理

**红色**部分，解决 `jar` 包中的**类加载**问题：

- 通过 [Archive](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/archive/Archive.java)，实现 `jar` 包的**遍历**，将 `META-INF/classes` 目录和 `META-INF/lib` 的每一个内嵌的 `jar` 解析成一个 Archive 对象。
- 通过 [Handler](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/jar/Handler.java)，处理 `jar:` 协议的 URL 的资源**读取**，也就是读取了每个 Archive 里的内容。
- 通过 [LaunchedURLClassLoader](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/LaunchedURLClassLoader.java)，实现 `META-INF/classes` 目录下的类和 `META-INF/classes` 目录下内嵌的 `jar` 包中的类的加载。具体的 URL 来源，是通过 Archive 提供；具体 URL 的读取，是通过 Handler 提供。

**橘色**部分，解决 Spring Boot 应用的**启动**问题：

- 通过 [MainMethodRunner](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/MainMethodRunner.java) ，实现 Spring Boot 应用的启动类的执行。

当然，上述的一切都是通过 [Launcher](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/Launcher.java) 来完成引导和启动，通过 `MANIFEST.MF` 进行具体配置。



 


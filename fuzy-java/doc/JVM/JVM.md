# JVM

## 前言

> 学习`Jvm`的目的，在平常代码开发中JVM基本上接触不到，也就偶尔线上调参需要使用到，对于大部分人来说知道几个调参的含义就可以了，那么为什么需要深入学习`Jvm`呢？

- 面试

  互联网软件研发越来越多，尽管大部分工作内容都不涉及这些知识，但是你仍然需要掌握别人不会的这样你脱颖而出的概率就越大（**变相促进内卷**）！

- 深入理解java这门语言

- 解决线上问题

## 1、类加载机制

首先我们看下以下的测试代码：

```java
package com.jvm.classloader;

class YeYe{
    static {
        System.out.println("YeYe静态代码块");
    }
}

class Father extends YeYe{
    public final static String strFather="HelloJVM_Father";

    static{
        System.out.println("Father静态代码块");
    }
}

class Son extends Father{
    public static String strSon="HelloJVM_Son";

    static{
        System.out.println("Son静态代码块");
    }
}

public class InitiativeUse {
    public static void main(String[] args) {
        System.out.println(Son.strFather);
    }
}
```

你以为的结果：

```java
Father静态代码块-》Son静态代码块-》HelloJVM_Father
```

实际运行结果：

```java
HelloJVM_Father
```

为什么会产生这个现象呢？这就涉及到类的加载机制。如下图，这是一个`Person.java`源文件被加载到`java`虚拟机中的整个流程：

```java
public class Person{
    private String name="Jack";
    private int age;
    private final double salary=100;
    private static String address;
    private final static String hobby="Programming";
    private static Object obj=new Object();
    public void say(){
        System.out.println("person say...");
    }
    public static int calc(int op1,int op2){
        op1=3;
        int result=op1+op2;
        Object obj=new Object();
        return result;
    }
    public static void main(String[] args){
        calc(1,2);
    }
}
```

![类加载机制](https://image-1301573777.cos.ap-chengdu.myqcloud.com/类加载机制.png)

​																																	**类加载流程**

首先我们需要明白运行时数据区域各个模块主要作用是什么？

### 1、1 运行时数据区

#### 1.1.1 Method Area（方法区）

- 方法区是各个线程共享的内存区域，在虚拟机启动时创建

- 它还有个别名叫Non-Heap(非堆)，目的是与java堆区分开

- 用于存储已被虚拟机加载的**类元数据、常量、静态变量、即时编译后的代码**等数据

  > **当方法区无法满足内存分配需求时，将抛出`OutOfMemoryError`异常。另外在JDK1.7中实际实现方法区的是永久代，在JDK1.8中是元空间。**

#### 1.1.2 Heap（堆）

- 时java虚拟机中管理内存最大的一块，在虚拟机启动时创建，被所有线程共享。
- Java对象实例以及数组都分配在堆上；**另外Class对象分配在堆上**

> **值得一提的是Class对象也是分配在堆上，在Java堆中生成代表这个类的`Java.lang.Class`对象，作为对方法区中这些数据的访问入口**

#### 1.1.3 Java Virtual Machine Stacks（虚拟机栈）

- 虚拟机栈是一个线程执行的区域，保存着一个线程中方法的调用状态。换句话说，**一个Java线程 的运行状态，由一个虚拟机栈来保存，所以虚拟机栈肯定是线程私有的，独有的，随着线程的创建而创建**。

- 每一个被线程执行的方法，为该栈中的栈帧，即每个方法对应一个**栈帧**（每个栈帧对应一个被调用的方法，可以理解为一个方法的运行空间）。

  ![image-20210124151342280](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210124151342280.png)

  ​																												**虚拟机栈**

  - **栈帧**

    1. **局部变量表**

       方法中定义的局部变量以及方法的参数存放在这张表中；局部变量表中的变量不可直接使用，如需要使用的话，必须通过相关指令将其加载至操作数栈中作为操作数使用。

    2. **操作数栈**

       以压栈和出栈的方式存储操作数的。

    3. **动态链接**

       每个栈帧都包含一个指向运行时常量池中该栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态连接(Dynamic Linking)。

    4. **方法返回地址**

       当一个方法开始执行后,只有两种方式可以退出，一种是遇到方法返回的字节码指令；一种是遇 见异常，并且这个异常没有在方法体内得到处理。

    ![image-20210124151630153](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210124151630153.png)

    ​																												**栈帧示意图**

    [栈帧调用过程栈帧变化详解](https://www.cnblogs.com/zlcxbb/p/5759776.html)

#### 1.1.4 The Pc Register（程序计数器）

> **我们都知道一个JVM进程中有多个线程在执行，而线程中的内容是否能够拥有执行权，是根据 CPU调度来的。 假如线程A正在执行到某个地方，突然失去了CPU的执行权，切换到线程B了，然后当线程A再获得CPU执行权的时候，怎么能继续执行呢？这就是需要在线程中维护一个变量，记录线程执行到 的位置。这就是程序计数器。如果线程正在执行Java方法，则计数器记录的是正在执行的虚拟机字节码指令的地址；如果正在执行的是native方法，则计数器为空。**

#### 1.1.5 Native Method Stacks（本地方法栈）

> **如果当前线程执行的方法是Native类型的，这些方法就会在本地方法栈中执行。 那如果在Java方法执行的时候调用native的方法呢？**

![image-20210124153006195](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210124153006195.png)

#### 1.1.6 其他内存模块

- 直接内存（Direct Memory）

  ​        **并不是虚拟机运行时数据区的一部分，也不是JVM规范中定义的内存区域，但是这部分内存也被频 繁地使用，而且也可能导致OutOfMemoryError 异常出现，所以我们放到这里一起讲解。在JDK 1.4 中新加入了NIO（New Input/Output）类，引入了一种基于通道（Channel）与缓冲区 （Buffer）的I/O 方式，它可以使用Native 函数库直接分配堆外内存，然后通过一个存储在Java 堆 里面的DirectByteBuffer 对象作为这块内存的引用进行操作。这样能在一些场景中显著提高性能， 因为避免了在Java 堆和Native 堆中来回复制数据。 本机直接内存的分配不会受到Java 堆大小的限制，但是，既然是内存，则肯定还是会受到本机总 内存的大小及处理器寻址空间的限制。因此在分配JVM空间的时候应该考虑直接内存所带来的影 响，特别是应用到NIO的场景。**

- 其他内存

  Code Cache：**JVM本身是个本地程序，还需要其他的内存去完成各种基本任务，比如，JIT 编译器在运行时对热点方法进行编译，就会将编译后的方法储存在Code Cache里面；GC等 功能。需要运行在本地线程之中，类似部分都需要占用内存空间。这些是实现JVM JIT等功能 的需要，但规范中并不涉及。**

#### 1.1.7 栈与堆、方法区之间的关系

- **栈指向堆**

  方法中，有如下代码`Object obj = new Object()`,此时局部变量`obj`指向堆中对象

- **方法区指向堆**

  有静态变量`private static Object = new Object()`，因为静态变量存储与方法区中，而对象实例存储与堆中，所以有方法区指向堆。

- **堆指向方法区**

  试想一下，方法区中会包含类的信息，堆中会有对象，那怎么知道对象是由哪个类创建的呢？所以，在对象的对象头中会有一个指针，用来指向方法区对应的类元数据信息。

### 1、2 类加载流程

了解了运行时数据区各部分功能后，我们分析**类加载流程图**中的执行流程：

#### 1.2.1 **javac编译器**

​	`Person.java`源文件被`javac`编译器编译成一个二进制文件，里面的内容是16进制。里面的内容不过多研究，想深入了解16进制对应的每块内容是什么可以参考这篇博客[Class文件十六进制背后的秘密](https://blog.csdn.net/peng_zhanxuan/article/details/104329859)

```tex
CA FE BA BE 00 00 00 34  00 43 0A 00 0A 00 2F 08
00 30 09 00 0D 00 31 06  40 59 00 00 00 00 00 00
09 00 0D 00 32 09 00 33  00 34 08 00 35 0A 00 36
00 37 07 00 38 0A 00 0D  00 39 09 00 0D 00 3A 07
00 3B 01 00 04 6E 61 6D  65 01 00 12 4C 6A 61 76
61 2F 6C 61 6E 67 2F 53  74 72 69 6E 67 3B 01 00
03 61 67 65 01 00 01 49  01 00 06 73 61 6C 61 72
79 01 00 01 44 01 00 0D  43 6F 6E 73 74 61 6E 74
56 61 6C 75 65 01 00 07  61 64 64 72 65 73 73 01
00 05 68 6F 62 62 79 08  00 3C 01 00 03 6F 62 6A
01 00 12 4C 6A 61 76 61  2F 6C 61 6E 67 2F 4F 62
6A 65 63 74 3B 01 00 06  3C 69 6E 69 74 3E 01 00
03 28 29 56 01 00 04 43  6F 64 65 01 00 0F 4C 69
6E 65 4E 75 6D 62 65 72  54 61 62 6C 65 01 00 12
......
```

#### 1.2.2 类的生命周期

> ​	当程序**主动**使用某个类时，如果该类还未被加载到内存中，则JVM会通过加载、连接、初始化3个步骤来对该类进行初始化。如果没有意外，JVM将会连续完成3个步骤，所以有时也把这个3个步骤统称为类加载或类初始化。**在java代码中，类型的加载、连接、与初始化过程都是在程序运行期间完成的（类从磁盘加载到内存中经历的三个阶段）。**

![image-20210124205138155](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210124205138155.png)

​																																	**类声明周期**

###### **装载**

​	装载阶段指的是将通过`javac`编译后的`.class`文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在堆区创建一个 `java.lang.Class`对象（JVM规范并未说明Class对象位于哪里，**HotSpot虚拟机将其放在方法区中**），用来封装类在方法区内的数据结构。类的加载的最终产品是位于堆区中的 `Class`对象， Class对象封装了类在方法区内的数据结构，并且向Java程序员提供了访问方法区内的数据结构的接口。

​	**该阶段也是可控性最强的阶段，开发人员可以使用系统提供的类加载器来完成，也可以自定义自己的类加载器来完成加载。加载阶段完成后，虚拟机外部的二进制字节流就按照虚拟机所需的格式存储在方法区之中，而且在Java堆中也创建一个 `java.lang.Class`类的对象，这样便可以通过该对象访问方法区中的这些数据**。

![image-20210124211046085](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210124211046085.png)

> **注意：Class对象就相当于一个模板，所有new出来的实例都是以这个模板来生成出来的。**

###### 链接

> 验证只要是为了确保Class文件中的字节流包含的信息完全符合当前虚拟机的要求，并且还要求我 们的信息不会危害虚拟机自身的安全，导致虚拟机的崩溃。

- 验证

  - 文件格式验证

    比如是否以16进制cafebaby开头；版本号是否正确等

  - 元数据验证

    保证不存在不符合java语法规范的元数据信息

  - 字节码验证

    进行数据流和控制流分析，确定程序语义是合法的、符合逻辑的。对类的方法体进行校验分 析，保证被校验的类的方法在运行时不会做出危害虚拟机安全的行为。

  - 符号引用验证

    常量池中描述类是否存在；访问的方法或者字段是否存在且具有足够的权限。

  > 可以通过-Xverify:none 取消验证。

- 准备

  ​	当完成字节码文件的校验之后，JVM 便会开始为类变量分配内存并初始化。**准备阶段是正式为类变量分配内存并设置类变量初始值的阶段，这些内存都将在方法区中分配**。

  - 为类变量（静态变量）分配内存并设置默认初始值

    | 数据类型            | 默认值   |
    | ------------------- | -------- |
    | int                 | 0        |
    | long                | 0L       |
    | short               | (short)0 |
    | char                | '\u0000' |
    | byte                | (byte)0  |
    | boolean             | false    |
    | float               | 0.0f     |
    | double              | 0.0d     |
    | reference(引用类型) | null     |

  - 这里不包含final修饰的类变量，因为final在编译的时候就分配了，准备阶段会显示初始化；

  - 这里不会为实例变量（也就是没加static）分配初始化，类变量会分配在方法区中，而实例变量是 会随着对象一起分配到Java堆中。

  > 需要注意的两点内存**分配的对象**以及**初始化的类型**

  **内存分配的对象**：要明白首先要知道Java 中的变量有**类变量**以及**类成员变量**两种类型，类变量指的是被 static 修饰的变量，而其他所有类型的变量都属于类成员变量。在准备阶段，JVM 只会为**类变量**分配内存，而不会为**类成员变量**分配内存。**类成员变量**的内存分配需要等到**初始化**阶段才开始，如下代码：

  ```java
  //准备阶段只会为static修饰的变量分配内存
  public static int LeiBianLiang = 666;
  public String ChenYuanBL = "jvm";
  ```

  **初始化的类型**：在准备阶段，JVM 会为类变量分配内存，并为其初始化（JVM 只会为类变量分配内存，而不会为类成员变量分配内存，类成员变量自然这个时候也不能被初始化）。但是这里的初始化指的是为变量赋予 Java 语言中该数据类型的默认值，而不是用户代码里初始化的值。如下代码：

  ```java
  //在准备阶段后，该变量的值是0而不是666
  public static int LeiBianLiang = 666;
  ```

  **注意**：**如果一个变量是常量（被 `static final` 修饰）的话，那么在准备阶段，属性便会被赋予用户希望的值。**

  ```java
  //在准备阶段后该变量的值是666，因为被final修饰的变量一旦赋值就不会再发生改变；
  public static final int ChangLiang = 666;
  ```

  [java中的Static、final、Static final各种用法](https://blog.csdn.net/qq_44543508/article/details/102691425)

- 解析

  ​	**[把类中的符号引用转换为直接引用](https://www.cnblogs.com/mzzcy/p/7223405.html)**；直接引用是与虚拟机内存布局实现相关，同一个符号引用在不同虚拟机实例上翻译出来的直接引用一般 不会相同，如果有了直接引用，那引用的目标必定存在内存中。另外这个阶段对于用户来说是透明(无法操作)的。

###### 初始化

> **注意这里的初始化与new出来的对象初始化不同，这里指的是类初始化。**

- 

JVM初始化步骤：

- 假如这个类还没有被加载和连接，则程序先加载并连接该类 
- 假如该类的直接父类还没有被初始化，则先初始化其直接父类 
- 假如类中有初始化语句，则系统依次执行这些初始化语句

###### 使用

在使用之前，需要了解**类初始化**与**对象初始化**的初始化内容：

- **类初始化：类变量的赋值语句，静态代码块设置初始值**

- **对象初始化：成员变量的赋值语句、普通代码块**

Java程序对类的使用方式可分为两种:**主动使用**与**被动使用**。

- 主动使用

  > 一般来说只有**当对类的首次主动使用的时候才会导致类的初始化**，所以**主动使用**又叫做**类加载过程中“初始化”开始的时机**。

  1. **创建类的实例，也就是new的方式**
  2. **访问某个类或接口的静态变量，或者对该静态变量赋值（static final 修饰的变量除外）**
  3. **调用类的静态方法**
  4. **反射**
  5. **初始化某个类的子类，父类也会被初始化**
  6. **java虚拟机启动时被标明为启动类的类（例如Main方法的类）**

- 被动引用

  1. **引用父类的静态字段，只会引起父类的初始化，而不会引起子类的初始化。** 
  2. **定义类数组，不会引起类的初始化。** 
  3. **引用类的static final常量，不会引起类的初始化（如果只有static修饰，还是会引起该类初始化 的）。**

  > **被动引用解释了开篇中的代码为什么输出的是`HelloJVM_Father`**。

再来看下面这个例子：

```java
package com.jvm.classloader;

import sun.applet.Main;

import java.util.Random;
import java.util.UUID;

class Test{
    static {
        System.out.println("static 静态代码块");
    }

    public static final double str=Math.random();  //编译期不确定
}


public class FinalUUidTest {
    public static void main(String[] args) {
        System.out.println(Test.str);
    }
}
```

输出：

```java
static 静态代码块
0.7338688977344875
```

按照**被动引用第三点**来说，应该不会输出静态代码块中的内容，那么为什么会产生这个结果呢？

> **其实final不是重点，重点是编译器把结果放入常量池！当一个常量的值并非编译期可以确定的，那么这个值就不会被放到调用类的常量池中，这时在程序运行时，会导致主动使用这个常量所在的类，也就是str需要程序运行才能确定这个值，所以这个类会被初始化。**

###### 卸载

在类使用完之后，如果满足下面的情况类就会被卸载：

- 该类所有的实例都已经被回收，也就是java堆中不存在该类的任何实例。 
- 加载该类的`ClassLoader`已经被回收。 
- 该类对应的`java.lang.Class`对象没有任何地方被引用，无法在任何地方通过反射访问该类的方法。

> Java虚拟机本身会始终引用这些类加载器，而这些类加载器则会始终引用它们所加载的类的Class 对象，因此这些Class对象始终是可触及的。

如果以上三个条件全部满足，java类的生命周期就结束了。**常见的结束生命周期情况**：

1、 执行了 System.exit()方法

2、 程序正常执行结束

3、 程序在执行过程中遇到了异常或错误而异常终止

4、 由于操作系统出现错误而导致Java虚拟机进程终止

### 1、3 类加载器

#### 什么是类加载器

- 负责读取 Java 字节代码，并转换成 java.lang.Class 类的一个实例的代码模块。 

- 类加载器除了用于加载类外，还可用于确定类在Java虚拟机中的唯一性。

> **一个类在同一个类加载器中具有唯一性(Uniqueness)，而不同类加载器中是允许同名类存在的， 这里的同名是指全限定名相同。但是在整个JVM里，纵然全限定名相同，若类加载器不同，则仍然不算作是同一个类，无法通过 instanceOf 、equals 等方式的校验。**

![image-20210125213524662](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210125213524662.png)

我们来看下下面代码：

```java
public class Demo3 {
    public static void main(String[] args) {
        //获取App ClassLoader
        ClassLoader classLoader= Demo3.class.getClassLoader();

        System.out.println(classLoader);
        //获取App ClassLoader的父加载器 Extension ClassLoader
        System.out.println(classLoader.getParent());
        //获取Extension ClassLoader的父加载器 Bootstrap ClassLoader
        System.out.println(classLoader.getParent().getParent());
    }
}
```

```java
sun.misc.Launcher$AppClassLoader@18b4aac2
sun.misc.Launcher$ExtClassLoader@4554617c
null
```

**为什么获取`Bootstrap ClassLoader`为空？**原因是Bootstrap Loader（启动类加载器）是用C++语言实现的（这里仅限于Hotspot，也就是JDK1.5之后默认的虚拟机，有很多其他的虚拟机是用Java语言实现的），找不到一个确定的返回父Loader的方式，于是就返回null。

#### JVM类加载机制的三种方式

- **全盘负责**

  当一个类加载器负责加载某个Class时，该Class所依赖的和引用的其他Class也将由该 类加载器负责载入，除非显示使用另外一个类加载器来载入。

- **双亲委派**（**重点**）

  指子类加载器如果没有加载过该目标类，就先委托父类加载器加载该目标类，只有在父类加载器找不到字节码文件的情况下才从自己的类路径中查找并装载目标类。

  > **双亲委派机制加载Class的具体过程：**
  >
  > **1、当`AppClassLoader`加载一个class时，它首先不会自己去尝试加载这个类，而是把类加载请求委派给父类加载器`ExtClassLoader`去完成。**
  >
  > **2、当`ExtClassLoader`加载一个class时，它首先也不会自己去尝试加载这个类，而是把类加载请求委派给`BootStrapClassLoader`去完成。**
  >
  > **3、如果`BootStrapClassLoader`加载失败（例如在 $JAVA_HOME/jre/lib里未查找到该class），会使用`ExtClassLoader`来尝试加载。**
  >
  > **4、若`ExtClassLoader`也加载失败，则会使用`AppClassLoader`来加载，如果`AppClassLoader`也加载失败,则会寻找自定义的类加载器，再不存在，则会报出异常`ClassNotFoundException`。**

  ![双亲委派加载机制](https://image-1301573777.cos.ap-chengdu.myqcloud.com/双亲委派加载机制.png)

  ​																														**双亲委派机制**

- **缓存机制**

  ​		缓存机制将会保证所有加载过的Class都将在内存中缓存，当程序中需要使用某个Class 时，类加载器先从内存的缓存区寻找该Class，只有缓存区不存在，系统才会读取该类对应的二进 制数据，并将其转换成Class对象，存入缓存区。这就是为什么修改了Class后，必须重启JVM，程 序的修改才会生效.对于一个类加载器实例来说，相同全名的类只加载一次，即`loadClass`方法不 会被重复调用。

#### 双亲委派机制

#### ![类加载器](https://image-1301573777.cos.ap-chengdu.myqcloud.com/类加载器.png)

​																								**类加载器结构**

其中核心代码则是`ClassLoader`中的`loadClass()`方法，该方法中的逻辑就是双亲委派机制的实现，下面分析下源码：

`java.lang.ClassLoader#loadClass(java.lang.String, boolean)`：根据name加载class

```java
public Class<?> loadClass(String name) throws ClassNotFoundException {
    return loadClass(name, false);
}

protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        // 先从缓存查找该class对象，找到就不用重新加载
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                if (parent != null) {
                    //如果找不到，则委托给父类加载器去加载
                    c = parent.loadClass(name, false);
                } else {
                    //如果没有父类，则委托给启动加载器去加载
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // 如果都没有找到，则通过自定义实现的findClass去查找并加载
                c = findClass(name);

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            //是否需要在加载时进行解析
            resolveClass(c);
        }
        return c;
    }
}
```

`java.lang.ClassLoader#findClass`：如果没找到则会抛出`ClassNotFoundException`异常

```java
//一般通过重写该方法来打破双亲委派机制
protected Class<?> findClass(String name) throws ClassNotFoundException {
    throw new ClassNotFoundException(name);
}
```

#### 自定义类加载器

```java
public class MyClassLoader extends ClassLoader {
    private String root;

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] loadClassData(String className) {
        String fileName = root + File.separatorChar
            + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public static void main(String[] args)  {

        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot("D:\\dirtemp");

        Class<?> testClass = null;
        try {
            testClass = classLoader.loadClass("com.yichun.classloader.Demo1");
            Object object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
```

> 1、这里传递的文件名需要是类的全限定性名称，即com.yichun.test.classloading.Test格式的，因为defineClass 方法是按这种格式进行处理的。 
>
> 2、最好不要重写`loadClass`方法，**因为这样容易破坏双亲委托模式**。
>
> 3、这类Test 类本身可以被`AppClassLoader`类加载，因此我们不能把com/yichun/test/classloading/Test.class放在类路径下。否则，由于双亲委托机制的存在，会直接导致该类由`AppClassLoader`加载，而不会通过我们自定义类加载器来加载。

**双亲委派的意义**

​	**假设用户自定一个类`java.lang.Integer`，通过双亲委派机制传到启动类加载器，而启动类在核心API发现这个类的名字，发现该类已被加载，就不会重新加载这个用户自定义的类，而是直接返回已加载过的`Integer.class`，这样可以防止核心API库被随意篡改。简而言之双亲委派的意义是：系统类防止内存中出现多份同样的字节码；保证Java程序安全稳定运行。**

## 2、JVM内存模型

![image-20210126223228939](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210126223228939.png)

​																																**JVM内存模型**

- 非堆（方法区）
- 堆
  - Old区
  - Young区
    - Eden区
    - S0和S1

### 2、1 对象的创建过程

​	**一般情况下，新创建的对象都会被分配到Eden区，一些特殊的大的对象会直接分配到Old区。**

![image-20210126230223412](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210126230223412.png)

​																																**对象创建过程**

【参考连接】：https://www.cnblogs.com/yichunguo/category/1591562.html
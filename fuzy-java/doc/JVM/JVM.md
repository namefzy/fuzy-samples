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





### 1、1 运行时数据区

> Java虚拟机在执行Java程序的过程中会把它管理的内存分为若干个不同的数据区域。这些区域有着各自的用途，一级创建和销毁的时间，有的区域随着虚拟机进程的启动而存在，有些区域则依赖用户线程的启动和结束而建立和销毁。如下图所示：

![image-20210511230440147](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210511230440147.png)

​																										**运行时数据区**

#### 1.1.1 Method Area（方法区）

- 方法区是各个线程共享的内存区域，在虚拟机启动时创建

- 它还有个别名叫Non-Heap(非堆)，目的是与java堆区分开

- 用于存储已被虚拟机加载的**类元数据、常量、静态变量、即时编译后的代码**等数据

  > **当方法区无法满足内存分配需求时，将抛出`OutOfMemoryError`异常。另外在JDK1.7中实际实现方法区的是永久代，在JDK1.8中是元空间。**

#### 1.1.2 Heap（堆）

- 是java虚拟机中管理内存最大的一块，在虚拟机启动时创建，被所有线程共享。
- Java对象实例以及数组都分配在堆上

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

![类加载机制](https://image-1301573777.cos.ap-chengdu.myqcloud.com/类加载机制.png)

​																															**类加载流程**

了解了运行时数据区各部分功能后，我们分析**类加载流程**中的执行流程：

#### 1.2.1 **javac编译器**

​	`Person.java`源文件被`javac`编译器编译成一个二进制文件，里面的内容是16进制。里面的内容不过多研究，想深入了解16进制对应的每块内容是什么可以参考这篇博客[Class文件十六进制背后的秘密](https://blog.csdn.net/peng_zhanxuan/article/details/104329859)，每块数字代表着对应的信息。

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

​	装载阶段指的是将通过`javac`编译后的`.class`文件中的二进制数据读入到内存中，将其放在运行时数据区的方法区内，然后在**方法区**创建一个 `java.lang.Class`对象（JVM规范并未说明Class对象位于哪里，**HotSpot虚拟机将其放在方法区中**），用来封装类在方法区内的数据结构。类的加载的最终产品是位于堆区中的 `Class`对象， Class对象封装了类在方法区内的数据结构，并且向Java程序员提供了访问方法区内的数据结构的接口。

​	**该阶段也是可控性最强的阶段，开发人员可以使用系统提供的类加载器来完成，也可以自定义自己的类加载器来完成加载。加载阶段完成后，虚拟机外部的二进制字节流就按照虚拟机所需的格式存储在方法区之中，而且在Java方法去中也创建一个 `java.lang.Class`类的对象，这样便可以通过该对象访问方法区中的这些数据**。

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

  - **这里不包含final修饰的类变量，因为final在编译的时候就分配了，准备阶段会显示初始化**；

  - 这里不会为实例变量（也就是没加static）分配初始化，类变量会分配在方法区中，而实例变量是 会随着对象一起分配到Java堆中。

  > 需要注意的两点内存**分配的对象**以及**初始化的类型**

  **内存分配的对象**：要明白首先要知道Java 中的变量有**类变量**以及**类成员变量**两种类型，**类变量指的是被 static 修饰的变量**，而其他所有类型的变量都属于类成员变量。在准备阶段，JVM 只会为**类变量**分配内存，而不会为**类成员变量**分配内存。**类成员变量**的内存分配需要等到**初始化**阶段才开始，如下代码：

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

  ​	**[把类中的符号引用转换为直接引用](https://www.cnblogs.com/mzzcy/p/7223405.html)**；直接引用是与虚拟机内存布局实现相关，同一个符号引用在不同虚拟机实例上翻译出来的直接引用一般不会相同，如果有了直接引用，那引用的目标必定存在内存中。另外这个阶段对于用户来说是透明(无法操作)的。

###### 初始化

> **注意这里的初始化与new出来的对象初始化不同，这里指的是类初始化。**

**初始化注意事项**

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

我们来看下面的代码示例

**示例一**

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
        //输出结果是HelloJVM_Father
        System.out.println(Son.strFather);
    }
}
```

**示例二**

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
        //static 静态代码块
		//0.7338688977344875
        System.out.println(Test.str);
    }
}
```

通过上面**被动引用**的分析，我们可以得出出示例一输出`HelloJVM_Father`；但按照**被动引用第三点**来说，示例二应该不会输出静态代码块中的内容，那么为什么会产生这个结果呢？

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

  ​		缓存机制将会保证所有加载过的Class都将在内存中缓存，当程序中需要使用某个Class 时，类加载器先从内存的缓存区寻找该Class，只有缓存区不存在，系统才会读取该类对应的二进制数据，并将其转换成Class对象，存入缓存区。这就是为什么修改了Class后，必须重启JVM，程序的修改才会生效.对于一个类加载器实例来说，相同全名的类只加载一次，即`loadClass`方法不会被重复调用。

#### 双亲委派机制

#### ![类加载器](https://image-1301573777.cos.ap-chengdu.myqcloud.com/类加载器.png)

​																								**类加载器结构**

其中核心代码则是`ClassLoader`中的`loadClass()`方法，**破化双亲委派机制也是重写改代码即可**。该方法中的逻辑就是双亲委派机制的实现，下面分析下源码：

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

**双亲委派的意义**	

​	**假设用户自定一个类`java.lang.Integer`，通过双亲委派机制传到启动类加载器，而启动类在核心API发现这个类的名字，发现该类已被加载，就不会重新加载这个用户自定义的类，而是直接返回已加载过的`Integer.class`，这样可以防止核心API库被随意篡改。简而言之双亲委派的意义是：系统类防止内存中出现多份同样的字节码；保证Java程序安全稳定运行。**

[破坏双亲委派](https://blog.csdn.net/cy973071263/article/details/104129163)

## 2、垃圾回收

​	垃圾回收（Garbage Collection，GC），顾名思义就是释放垃圾占用的空间，防止内存泄露。有效的使用可以使用的内存，对内存堆中已经死亡的或者长时间没有使用的对象进行清除和回收。那么如何确定这个对象被`jvm`看作是垃圾？垃圾回收发生java内存中的哪块区域？什么样的对象会被垃圾回收？带着这些疑问我们来看下面的文章。

### 堆结构

在`jvm`运行时数据区那一章节我们分析了`new`出来的对象都是存在堆中的，那么堆的结构分为哪些呢？如下图：

![image-20210126223228939](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210126223228939.png)

​																											       **堆结构**	

- **非堆（方法区）**

  **存放类信息，静态变量、常量以及编译时即时信息等。**

- **堆**

  - **Old区**

    **存放大龄对象，以及一些特殊的大的对象**

  - **Young区**

    - **Eden区**

      **对象的出生地**

    - **S0和S1**

      **GC时用来搬运对象的空间**

###  对象的创建过程

​	**一般情况下，新创建的对象都会被分配到Eden区，一些特殊的大的对象会直接分配到Old区。**

```tex
我是一个普通的Java对象,我出生在Eden区,在Eden区我还看到和我长的很像的小兄弟,我们在Eden区中玩了
挺长时间。有一天Eden区中的人实在是太多了,我就被迫去了Survivor区的“From”区,自从去了Survivor
区,我就开始漂了,有时候在Survivor的“From”区,有时候在Survivor的“To”区,居无定所。直到我18岁的
时候,爸爸说我成人了,该去社会上闯闯了。于是我就去了年老代那边,年老代里,人很多,并且年龄都挺大的。
```

![image-20210126230223412](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210126230223412.png)

​																									     **对象创建过程**

### 如何确定一个对象是垃圾？

- 引用计数法

  引用计数法很简单，如下代码：

  ```java
  Object object = new Object();
  //业务逻辑代码.....
  object =null;
  ```

  ​	当引用`new出来的Object对象`没有被任何引用指向时，就认为该对象是垃圾对象。但是该种方法缺点也很明显，当A对象与B对象相互持有对象时，导致永远不能回收。如下图：
  ![image-20210130112215177](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130112215177.png)

  ​																										**相互引用**

  如上图所示，采用引用计数法时，有以下步骤：

  - `a = new A()`； a的引用+1； count=1
  - `b.setA(a)`； a的引用+1；     count=2
  - a=null； a的引用-1；           count=1

  当a=null时，如果此时发生垃圾回收，由于a的引用不为0，依然不会被回收。此时就会造成内存泄漏。

- 可达性算法

  ​		**通过一系列的GC Roots（全局视角）的对象作为起始点，从这些根节点开始向下搜索，搜索所走过的路径称为引用链（Reference Chain），当一个对象到GC Roots没有任何引用链相连时，则证明此对象是不可用的。**

  ![image-20210130114455778](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130114455778.png)

  ​																												**可达性算法 GC Root**

  **分析：**

  - **将方法区中`D`看作是`GC Root`，那么从D(引用)->D(堆中对象)->C(堆中对象)，这条路线上的所有对象都是可用的；**
  - **F->C；因为F没有被外部引用，所以F被视为不可用**
  - **再来分析下之前引用计数法遗留的问题（相互引用），由于使用了可达性分析算法，那么A对象与B对象都没有被GC ROOT引用链关联，所以堆中的A与B都会被视为不可用，从而会被垃圾回收；**

那么`jvm`中规定了哪些可以属于`GC Root`呢？

- Class -由系统类加载器加载的对象，这些类是不能被回收的，他们可以以静态字段的方式保持其他 持有对象。
- Thread -活着的线程 
- Stack Local - Java 方法的 local 变量或者参数 
- JNI Gloabl -全局 JNI 引用 
- Monitor Used -用于同步的监控对象 
- Held by JVM -用于 JVM 特殊目的由 GC 保留的对象，但实际上这个与 JVM 实现有关联。

### 什么时候垃圾回回收？

- Young区或者Old区空间不足
- 方法区空间不足
- 主动调用`System.gc()`

### 常见问题

- **Young区为什么分为Eden区与S区？**

  > ​		**如果没有Young没有分区，那么每次发生一次GC都会导致存活对象被送入Old区，这样老年代很快被填满，触发Major GC(发生在old区的垃圾回收，该GC一般伴随着Full GC，Full GC=old区gc+young区gc)，频繁触发会影响程序的执行和相应速度。所以Survivor（存放活着的对象）的存在意义,就是减少被送到老年代的对象,进而减少Full GC的发生。Survivor的预筛选保证,只有经历16次Minor GC（发生在Young区的垃圾回收）还能在新生代中存活的对象,才会被送到老年代。**

- **为什么设置两个Survivor区？**

  > **假设现在只有一个survivor区，我们来模拟一下流程：
  > 		新建的对象在Eden中，一旦Eden满了，触发一次Minor GC，Eden中的存活对象就会被移动到Survivor区。这样继续循环下去，下一次Eden满了的时候，问题来了，此时进行Minor GC，Eden和Survivor各有一些存活对象，如果此时把Eden区的存活对象硬放到Survivor区，很明显这两部分对象所占有的内存是不连续的，也就导致了内存碎片化。也许我们会问为什么不在每次GC的时候进行重排列？因为Minor GC会比较频繁，相比较重排列，直接复制对性能损耗低。**

  ![image-20210127225017782](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210127225017782.png)

  ​																										**S区只有一块的情况**

![image-20210127225241502](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210127225241502.png)

​																													**S区分成两块的情况**

​			**所以S区分成两块的根本目的是为了保证S区无碎片化。**

- **新生代中Eden:S0:S1为什么是8:1:1？**

  > ​		新生代中的可用内存：复制算法用来担保的内存为9:1 可用内存中Eden：S1区为8:1 即新生代中Eden:S0:S1 = 8:1:1 现代的商业虚拟机都采用这种收集算法来回收新生代，IBM公司的专门研究表明，新生代中的对象大概98%是 “朝生夕死”的。

参考链接:https://www.yourkit.com/docs/java/help/gc_roots.jsp

## 3、垃圾收集算法

#### 标记-清除

> **找出内存中需要回收的对象，并且把他们标记出来；此时堆中所有的对象都会被扫描一遍，从而确定需要回收的对象，比较耗时。另外此算法会产生大量的空间碎片。**

- **标记阶段**

  ![image-20210130142513882](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130142513882.png)

- **清除阶段**

  ![image-20210130142543198](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130142543198.png)

#### 标记-复制

> **将内存划分为两块相等的区域，每次只使用其中的一块。但该算法比较消耗空间。**

- 标记阶段

  ![image-20210130142954421](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130142954421.png)

- 复制阶段

  **如下图，将所有存活对象移到右边，同时回收左边的垃圾。**

  ![image-20210130143019975](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130143019975.png)

#### 标记-整理

> **区别于标记复制算法，标记-整理算法是先标记存活对象，然后将所有存活对象移动到一块连续区域，然后清理掉存活区域边界以外的内存。**

- 标记阶段

  ![image-20210130143543684](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130143543684.png)

- 整理阶段

  ![image-20210130143604760](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130143604760.png)

### 分代收集

> **针对堆中不同区域，制定不同算法。**
>
> Young区：对象特点大多数都是朝生夕死，复制算法效率高。
>
> Old区：该区域都是存活时间比较长的对象，一般发生垃圾回收的频率相对来说较低；所以采取标记清除或者标记整理算法。

## 4、垃圾收集器

> 如果说垃圾收集算法是内存回收的策略，那么垃圾收集器就是内存回收的具体实现。

![image-20210130145040032](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130145040032.png)

如上图展示了堆内存中不同区域可使用的垃圾收集器策略。

- Young区：`Serial、ParNew、Parallel Scavenge`
- Old区：`CMS、Serial Old、Parallel Old`
- G1既能在老年代中被使用也能在新生代中被使用。

#### `Serial`收集器

> 它是一种单线程收集器，不仅仅意味着它只会使用一个CPU或者一条收集线程去完成垃圾收集工作，更 重要的是其在进行垃圾收集的时候需要暂停其他线程。
>
> - 优点：简单高效，拥有很高的单线程收集效率 
> - 缺点：**收集过程需要暂停所有线程** 
> - 算法：新生代中复制算法；老年代中使用标记整理算法 
> - 适用范围：堆 
> - 应用：Client模式下的默认新生代收集器

![image-20210130150053236](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130150053236.png)

#### `ParNew`收集器

> 可以把这个收集器理解为Serial收集器的多线程版本。
>
> - 优点：在多CPU时，比Serial效率高。 
> - 缺点：收集过程暂停所有应用程序线程，单CPU时比Serial效率差。 
> - 算法：复制算法 
> - 适用范围：新生代 
> - 应用：运行在Server模式下的虚拟机中首选的新生代收集器

![image-20210130145921347](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130145921347.png)

#### `Parallel Scavenge`与`Parallel Old`收集器

​		`Parallel Scavenge`收集器是一个新生代收集器，它也是使用复制算法的收集器，又是并行的多线程收集 器，看上去和`ParNew`一样，但是`Parallel Scanvenge`更关注系统的吞吐量。

​		`Parallel Old`是老年代的收集器。使用多线程和标记-整理算法进行垃圾回 收，也是更加关注系统的吞吐量。

> 吞吐量=运行用户代码的时间/(运行用户代码的时间+垃圾收集时间) 
>
> 比如虚拟机总共运行了100分钟，垃圾收集时间用了1分钟，吞吐量=(100-1)/100=99%。
>
> 若吞吐量越大，意味着垃圾收集的时间越短，则用户代码可以充分利用CPU资源，尽快完成程序 的运算任务。
>
> ```java
> -XX:MaxGCPauseMillis控制最大的垃圾收集停顿时间，
> -XX:GCTimeRatio直接设置吞吐量的大小。
> ```

#### `CMS`收集器

> `CMS(Concurrent Mark Sweep)`收集器是一种以获取 最短回收停顿时间 为目标的收集器。 采用的是"**标记-清除算法**",整个过程分为4步
>
> ```ABAP
> (1)初始标记 CMS initial mark 标记GC Roots直接关联对象，不用Tracing，速度很快
> (2)并发标记 CMS concurrent mark 进行GC Roots Tracing(找出存活对象,所有GC Root链上的都为存活对象)
> (3)重新标记 CMS remark 修改并发标记因用户程序变动的内容
> (4)并发清除 CMS concurrent sweep 清除不可达对象回收空间，同时有新垃圾产生，留着下次清理称为浮动垃圾
> ```
>
> 由于整个过程中，并发标记和并发清除，收集器线程可以与用户线程一起工作，所以总体上来 说，CMS收集器的内存回收过程是与用户线程一起并行地执行的。
>
> 优点：并发收集、停顿低
>
> 缺点：产生大量碎片，降低系统吞吐量

![image-20210130183843813](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130183843813.png)

#### `G1(Garbage-First)`

​	使用G1收集器时，Java堆的内存布局与就与其他收集器有很大差别，**它将整个Java堆划分为多个大小相等的独立区域（Region），虽然还保留有新生代和老年代的概念，但新生代和老年代不再 是物理隔离的了，它们都是一部分Region（不需要连续）的集合。所谓的G1就是优先回收垃圾最多的Region区域。**

​	**值得注意的是每个Region大小都是一样的，可以是1M到32M之间的数值，但是必须保证是2的n次幂；如果对象太大，一个Region放不下[超过Region大小的50%]，那么就会直接放到H中。**

![image-20210130185724499](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130185724499.png)

​																										**G1收集器堆内存布局**

**G1的工作流程如下：**

```ABAP
(1)初始标记（Initial Marking） 标记以下GC Roots能够关联的对象，并且修改TAMS的值，需要暂停用户线程
(2)并发标记（Concurrent Marking） 从GC Roots进行可达性分析，找出存活的对象，与用户线程并发执行
(3)最终标记（Final Marking） 修正在并发标记阶段因为用户程序的并发执行导致变动的数据，需暂停用户线程
(4)筛选回收（Live Data Counting and Evacuation） 对各个Region的回收价值和成本进行排序，根据用户所期望的GC停顿时间制定回收计划
```

![image-20210130190140097](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130190140097.png)

​																													**G1垃圾回收流程**

#### `ZGC`收集器

​	JDK11新引入的ZGC收集器，不管是物理上还是逻辑上，ZGC中已经不存在新老年代的概念了 会分为一个个page，当进行GC操作时会对page进行压缩，因此没有碎片问题只能在64位的linux上使用，目前用得还比较少。

优点：

> （1）可以达到10ms以内的停顿时间要求
>
> （2）支持TB级别的内存
>
> （3）堆内存变大后停顿时间还是在10ms以内

#### 常见问题

- **如何选择垃圾回收器**

  ```ABAP
  优先调整堆的大小让服务器自己来选择
  如果内存小于100 M ，使用串行收集器
  如果是单核，并且没有停顿时间要求，使用串行或 JVM 自己选
  如果允许停顿时间超过1秒，选择并行或 JVM 自己选
  如果响应时间最重要，并且不能超过1秒，使用并发收集器
  ```

- **如何开启需要的垃圾回收器**

  ```shell
  （1）串行
  -XX：+UseSerialGC
  -XX：+UseSerialOldGC
  （2）并行(吞吐量优先)：
  -XX：+UseParallelGC
  -XX：+UseParallelOldGC
  （3）并发收集器(响应时间优先)
  -XX：+UseConcMarkSweepGC
  -XX：+UseG1GC
  ```

- Young区GC会发生`stop the world`吗？

  > 不管什么 GC，都会发送 stop-the-world，区别是发生的时间长短。而这个时间跟垃圾收集器又有关 系，Serial、PartNew、Parallel Scavenge 收集器无论是串行还是并行，都会挂起用户线程，而 CMS 和 G1 在并发标记时，是不会挂起用户线程的，但其它时候一样会挂起用户线程，stop the world 的时 间相对来说就小很多了。

- major GC 和full GC的区别

  > Major GC在很多参考资料中是等价于 Full GC 的。一般情况下，一次 Full GC 将会对年轻代、老年代、元空间以及堆外内存进行垃圾回收。触 发 Full GC 的原因有很多：当年轻代晋升到老年代的对象大小，并比目前老年代剩余的空间大小还要大 时，会触发 Full GC；当老年代的空间使用率超过某阈值时，会触发 Full GC；当元空间不足时（JDK1.7 永久代不足），也会触发 Full GC；当调用`System.gc()`也会安排一次 Full GC。

## 5、JVM实战

#### 常用命令

- `jps`

  > 查看java进程

- `jinfo`

  > **实时查看和调整JVM配置参数**
  >
  > 用法：`jinfo -flag name PID` 查看某个java进程的name属性的值
  >
  > ```shell
  > #查看属性值
  > jinfo -flag MaxHeapSize PID
  > jinfo -flag UseG1GC PID
  > #修改
  > jinfo -flag [+|-] PID
  > jinfo -flag <name>=<value> PID
  > ```

- `jstat`

  - 查看虚拟机性能统计信息

  - 查看类装载信息

    ```shell
    jstat -class PID 1000 10 查看某个java进程的类装载信息，每1000毫秒输出一次，共输出10次
    ```

  - 查看垃圾收集信息

    ```shell
    jstat -gc PID 1000 10
    ```

- `jstack`

  > 查看线程堆栈信息；还可以排查死锁。

  ```shell
  jstack PID
  ```

- `jmap`

  - 打印出堆内存相关信息

    ```shell
    jmap -heap PID
    ```

  - dump出堆内存相关信息

    ```shell
    jmap -dump:format=b,file=heap.hprof PID
    ```

####  常用工具

##### `jconsole`

​	`JConsole`工具是JDK自带的可视化监控工具。查看java应用程序的运行概况、监控堆信息、永久区使用 情况、类加载情况等。命令行输入jconsole即可。

##### `jvisualvm`

​	可以监控java进程的CPU、类、线程、堆栈信息以及`dupm`文件等。命令行输入`jvisualvm`命令即可，不过需要下载对应的插件，版本要对应的上。插件下载地址：https://visualvm.github.io/pluginscenters.html

**监控远端Java进程**

- 在`visualvm`中选中“远程”，右击“添加” 

- 主机名上写服务器的`ip`地址，比如39.100.39.63，然后点击“确定”

- 右击该主机"39.100.39.63"，添加“JMX”，也就是通过JMX技术具体监控远端服务器哪个Java进程

- 要想让服务器上的tomcat被连接，需要改一下Catalina.sh这个文件

  > 注意下面的8998不要和服务器上其他端口冲突
  >
  > ```xml-dtd
  > JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote -
  > Djava.rmi.server.hostname=39.100.39.63 -Dcom.sun.management.jmxremote.port=8998
  > -Dcom.sun.management.jmxremote.ssl=false -
  > Dcom.sun.management.jmxremote.authenticate=true -
  > Dcom.sun.management.jmxremote.access.file=../conf/jmxremote.access -
  > Dcom.sun.management.jmxremote.password.file=../conf/jmxremote.password"
  > ```

- `在../conf文件中添加两个文件jmxremote.access和jmxremote.password；授予权限chmod 600 jmxremot`

  `jmxremote.access`文件

  ```access
  guest readonly
  manager readwrite
  ```

  `jmxremote.password`文件

  ```pascal
  guest guest
  manager manager
  ```

- 将连接服务器地址改为公网`ip`地址

  ```shell
  hostname -i 查看输出情况
  172.26.225.240 172.17.0.1
  vim /etc/hosts
  172.26.255.240 39.100.39.63
  
  ```

- 设置上述端口对应的阿里云安全策略和防火墙策略

- 启动tomcat

- 在刚才的JMX中输入8998端口，并且输入用户名和密码则登录成功

  ```properties
  端口:8998
  用户名:manager
  密码:manager
  ```

##### `arthas`

> `github`：https://github.com/alibaba/arthas
>
> `Arthas`是Alibaba开源的Java诊断工具，采用命令行交互模式，是排查`jvm`相关问题的利器。

##### `jprfiler`

> idea中集成的分析内存的软件，不过是收费的。如下图是该工具的部分功能展示。

![image-20210130222406450](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130222406450.png)

##### 其他相关工具

heaphero：https://heaphero.io/

perfma：https://console.perfma.com/

gceasy：http://gceasy.io

gcplot：https://it.gcplot.com/

### JVM性能优化指南

![image-20210130222931415](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20210130222931415.png)

【参考连接】：https://www.cnblogs.com/yichunguo/category/1591562.html
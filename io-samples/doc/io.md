[TOC]

# IO

## BIO

### IO的用途和特征

- 文件访问
- 网络访问
- 内存缓存访问
- 线程内部通信(管道)
- 缓冲
- 过滤
- 解析
- 读写文本 (Readers / Writers)
- 读写基本类型数据 (long, int etc.)
- 读写对象

### IO概述

|                    | 字节输入流                              | 字节输出流                            | 字符输入流                          | 字符输出流                 |
| ------------------ | --------------------------------------- | :------------------------------------ | ----------------------------------- | -------------------------- |
| **基本**           | `InputStream`                           | `OutputStream`                        | `ReaderInputStreamReader`           | `WriterOutputStreamWriter` |
| **数组**           | `ByteArrayInputStream`                  | `ByteArrayOutputStream`               | `CharArrayReader`                   | `CharArrayWriter`          |
| **文件**           | `FileInputStream` `RandomAccessFile`    | `FileOutputStream` `RandomAccessFile` | `FileReader`                        | `FileWriter`               |
| **Pipes**          | `PipedInputStream`                      | `PipedOutputStream`                   | `PipedReader`                       | `PipedWriter`              |
| **Buffering**      | `BufferedInputStream`                   | `BufferedOutputStream`                | `BufferedReader`                    | `BufferedWriter`           |
| **Filtering**      | `FilterInputStream`                     | `FilterOutputStream`                  | `FilterReader`                      | `FilterWriter`             |
| **Parsing**        | `PushbackInputStream` `StreamTokenizer` |                                       | `PushbackReader` `LineNumberReader` |                            |
| **Strings**        |                                         |                                       | `StringReader`                      | `StringWriter`             |
| **Data**           | `DataInputStream`                       | `DataOutputStream`                    |                                     |                            |
| **Data-Formatted** |                                         | `PrintStream`                         |                                     | `PrintWriter`              |
| **Objects**        | `ObjectInputStream`                     | `ObjectOutputStream`                  |                                     |                            |
| **Utilities**      | `SequenceInputStream`                   |                                       |                                     |                            |

### IO类型

#### 基础IO

##### 文件IO

- `FileInputStream`和``FileOutputStream` `：可以读取任何文件

  - `read()`和`write()`

    > 1.从流读取的是一个字节
    >
    > 2.返回的是字节的(0-255)内的字节值
    >
    > 3.读一个下次就自动到下一个,如果碰到-1说明没有值了.

    ```java
    public static void singleByteIO(FileInputStream fis, FileOutputStream fos) throws IOException {
        int data = fis.read();
        while (data!=-1){
            System.out.println((char) data);
            fos.write(data);
            data = fis.read();
        }
    }
    ```

  - `read(byte[] bytes)`和`write(byte[] byte)`

    > 1.从读取流读取一定数量的字节,如果比如文件总共是102个字节
    >
    > 2.我们定义的数组长度是10,那么默认前面10次都是读取10个长度
    >
    > 3.最后一次不够十个,那么读取的是2个
    >
    > 4.这十一次,每次都是放入10个长度的数组

    ```java
    public static void multiByteIO(InputStream fis, FileOutputStream fos) throws IOException {
        //fis.available()这个方法可以在读写操作前先得知数据流里有多少个字节可以读取；在文本操作中不会发生问题；用于网络操作会出现问题
        //bytes将流中读取的数据存储在字节数组中,每次读取都会将字节数组从头开始重新赋值
        byte[] bytes = new byte[fis.available()];
        int len = 0;
        while ((len=fis.read(bytes))!=-1){
            fos.write(bytes);
            System.out.println(new String(bytes,0,len));
        }
    }
    
    ```

  - `read(byte[] bytes,int offset,int len)`和`write(byte[] bytes,int offset,int len)`

    > 1.从读取流读取一定数量的字节,如果比如文件总共是102个字节
    >
    > 2.我们定义的数组长度是10,但是这里我们写read(bytes,0,9)那么每次往里面添加的(将只会是9个长度),就要读12次,最后一次放入3个.
    >
    > 3.所以一般读取流都不用这个而是用上一个方法:read(byte[])

    ```java
    public static void multiByteIO(FileInputStream fis, FileOutputStream fos)throws Exception {
        byte[] bytes = new byte[3];
        int len = 0;
        while ((len=fis.read(bytes,0,2))!=-1){
            fos.write(bytes,0,len);
            System.out.println(new String(bytes,0,len));
        }
    }
    ```

- `FileReader`和`FileWriter`：处理文本文件

  > 与`InputStream`和`OutputStream`流类似，它也有对应的三种读取方法。下文只展示了`read(char[] chars)`方法

  ```java
  public static void main(String[] args) throws IOException {
      FileReader fr = new FileReader("C:\\Users\\fuzy\\Desktop\\data.txt");
      FileWriter fw = new FileWriter("C:\\Users\\fuzy\\Desktop\\copy.txt");
      try{
          char[] chars = new char[1024];
          int read = 0;
          while ((read=fr.read(chars))!=-1){
              fw.write(chars);
              System.out.println(new String(chars));
          }
      }catch (Exception e){
          System.err.println(e.getMessage());
      }finally {
          fr.close();
          fw.close();
      }
  
  }
  ```

- `RandomAccessFile`

  > 即可以读取文件内容，也可以向文件中写入内容。但是和其他输入/输入流不同的是，程序可以直接跳到文件的任意位置来读写数据。`RandomAccessFile`类允许自由定位文件记录指针，所以RandomAccessFile可以不从文件开始的地方进行输出，所以`RandomAccessFile`可以向已存在的文件后追加内容。

  - 从指定位置读取文件

    ```java
    public static void main(String[] args)throws Exception {
        //“r” 以只读方式来打开指定文件夹。如果试图对该RandomAccessFile执行写入方法，都将抛出IOException异常。
        //“rw” 以读，写方式打开指定文件。如果该文件尚不存在，则试图创建该文件。
        //“rws” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容或元数据的每个更新都同步写入到底层设备。
        //“rwd” 以读，写方式打开指定文件。相对于”rw” 模式，还要求对文件内容每个更新都同步写入到底层设备。
        RandomAccessFile raf = new RandomAccessFile(new File("C:\\Users\\fuzy\\Desktop\\data.txt"),"rw");
        try{
            //移动到指定位置开始读取
            raf.seek(4);
            byte[] b=new byte[1024];
            int hasRead=0;
            //循环读取文件
            while((hasRead=raf.read(b))>0){
                //输出文件读取的内容
                System.out.print(new String(b,0,hasRead));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            raf.close();
        }
    
    
    }
    ```

  - 向文件中追加内容

    > **注：`RandomAccessFile`不能向文件的指定位置插入内容，如果直接将文件记录指针移动到中间某位置后开始输出，则新输出的内容会覆盖文件原有的内容，如果需要向指定位置插入内容，程序需要先把插入点后面的内容写入缓存区，等把需要插入的数据写入到文件后，再将缓存区的内容追加到文件后面。**

    ```java
    public static void insert(String filePath,long pos,String insertContent)throws IOException {
        RandomAccessFile raf=null;
        File tmp=File.createTempFile("tmp",null);
        tmp.deleteOnExit();
        try {
            // 以读写的方式打开一个RandomAccessFile对象
            raf = new RandomAccessFile(new File(filePath), "rw");
            //创建一个临时文件来保存插入点后的数据
            FileOutputStream fileOutputStream = new FileOutputStream(tmp);
            FileInputStream fileInputStream = new FileInputStream(tmp);
            //把文件记录指针定位到pos位置
            raf.seek(pos);
            raf.seek(pos);
            //------下面代码将插入点后的内容读入临时文件中保存-----
            byte[] bbuf = new byte[3];
            //用于保存实际读取的字节数据
            int hasRead = 0;
            //使用循环读取插入点后的数据
            while ((hasRead = raf.read(bbuf)) != -1) {
                //将读取的内容写入临时文件
                fileOutputStream.write(bbuf, 0, hasRead);
            }
            //-----下面代码用于插入内容 -----
            //把文件记录指针重新定位到pos位置
            raf.seek(pos);
            //追加需要插入的内容
            raf.write(insertContent.getBytes());
            //追加临时文件中的内容
            while ((hasRead = fileInputStream.read(bbuf)) != -1) {
                //将读取的内容写入临时文件
                raf.write(bbuf, 0, hasRead);
            }
        }catch (Exception e){
            throw  e;
        }
    }
    ```


##### 管道IO

> Java IO中的管道为运行在同一个JVM中的两个线程提供了通信的能力；你不能利用管道与不同的JVM中的线程通信(不同的进程)。
>
> 一个`PipedInputStream`流应该和一个`PipedOutputStream`流相关联。一个线程通过`PipedOutputStream`写入的数据可以被另一个线程通过相关联的`PipedInputStream`读取出来，如下图：
>
> ![image-20211031195141589](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211031195141589.png)

- `PipedInputStream`和`PipedOutputStream`

  ```java
  public static void main(String[] args) throws Exception{
      final PipedOutputStream pos = new PipedOutputStream();
      final PipedInputStream pis = new PipedInputStream(pos);
      Thread thread1 = new Thread(() -> {
          try {
              pos.write("Hello World!".getBytes());
          } catch (IOException e) {
              e.printStackTrace();
          }finally {
              try {
                  pos.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      });
  
      Thread thread2 = new Thread(() -> {
          try {
              int data = pis.read();
              while (data!=-1){
                  System.out.println((char)data);
                  data = pis.read();
              }
          } catch (IOException e) {
              e.printStackTrace();
          }finally {
              try {
                  pis.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      });
      thread1.start();
      thread2.start();
  }
  ```

- `PipedReader`和`PipedWriter`

##### 数组IO

> 字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中。创建字节数组输出流对象有以下几种方式。

- `ByteArrayOutputStream`和`ByteArrayInputStream`

  ```java
  public static void main(String[] args)throws Exception {
      //创建一个大小为 a 字节的缓冲区。
      ByteArrayOutputStream output = new ByteArrayOutputStream(12);
      while (output.size()!=10){
          //获取用户输入
          output.write(System.in.read());
      }
      byte[] bytes = output.toByteArray();
      int c;
      ByteArrayInputStream input = new ByteArrayInputStream(bytes);
      System.out.println("Converting characters to Upper case " );
      while(( c= input.read())!= -1) {
          System.out.println(Character.toUpperCase((char)c));
      }
      output.close();
      input.close();
  }
  ```

- `CharArrayReader`和`CharArrayWriter`

#### 包装类型IO

##### BufferInputStream

> ==`BufferedInputStream`能为输入流提供缓冲区，能提高很多IO的速度。你可以一次读取一大块的数据，而不需要每次从网络或者磁盘中一次读取一个字节。特别是在访问大量磁盘数据时，缓冲通常会让IO快上许多。==

- `BufferedInputStream`和`BufferedOutputStream`

  ```java
  public static void main(String[] args)throws Exception {
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\Users\\fuzy\\Desktop\\data.txt"));
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\fuzy\\Desktop\\copy.txt"));
  
      byte[] buffer = new byte[1024];
      int copySize;
  
      while ((copySize = bis.read(buffer)) > 0){
          bos.write(buffer, 0, copySize);
      }
      bis.close();
      bos.close();
  }
  ```

- `BufferedReader`和`BufferedWriter`

- 与基本`InputStream`和`OutputStream`比较

  |          | 基本`InputStream`                                            | `BufferedInputStream`                                        |
  | -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 执行流程 | ![image-20211031203803688](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211031203803688.png) | ![image-20211031204554833](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211031204554833.png) |
  | 概述     | 每次读取对应一个字节（或字节数组）的大小，每次读取量比较小的情况下会与磁盘发生多次IO | 默认的缓冲区大小是8192，即每次读取8192个字节到缓冲区；然后中转站再从内存中读取 |
  | 临界值   | 当定义的数组大小超过`BufferedInputStream`的默认缓冲区大小时，两者的性能基本一致； |                                                              |
  | `api`    |                                                              | `readLine()`：按行读取                                       |

##### DataInputStream

> 相比较其他io，该包装类型io提供了读取基本类型的方法。

- `DataInputStream`和`DataOutputStream`

  ```java
  public static void main(String[] args)throws Exception {
      DataInputStream bis = new DataInputStream(new FileInputStream("C:\\Users\\fuzy\\Desktop\\copy.txt"));
      DataOutputStream bos = new DataOutputStream(new FileOutputStream("C:\\Users\\fuzy\\Desktop\\copy.txt"));
  
      bos.writeInt(123);
      bos.writeUTF("中华");
      bos.writeDouble(12.1);
  
      System.out.println(bis.readInt());
      System.out.println(bis.readUTF());
      System.out.println(bis.readDouble());
  
      bis.close();
      bos.close();
  }
  ```

##### ObjectInputStream

- `ObjectInputStream`和`ObjectOutputStream`

  > `ObjectInputStream `：将序列化的原始数据恢复成对象
  >
  > `ObjectOutputStream`: 将对象序列化

  **employee**

  ```java
  //序列化的类必须要实现Serializable接口
  public class Employee implements Serializable {
      //类似于版本号，当该值发生修改后；继续读取变化前的copy.txt，会发生异常
      private static final long serialVersionUID = -2301305139017630676L;
      
      //transient：反序列化,字段加上该属性后，该字段的值不会被序列化到文本中
      private transient String name;
  
      private int id;
  
      public Employee(String name,int id){
          this.name = name;
          this.id = id;
      }
  
      public void showInfo(){
          System.out.println("name:" + name + "\tid:" + id );
      }
  }
  ```

  **bootstrap类**

  ```java
  public static void main(String[] args)throws Exception {
      writeObject();
      readObject();
  }
  
  /**
    * 将对象序列化到copy.txt文件中
    * @throws Exception
    */
  private static void writeObject()throws Exception {
      FileOutputStream fos = new FileOutputStream("C:\\Users\\fuzy\\Desktop\\copy.txt");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(new Employee("张三",1));
      oos.writeObject(new Employee("李四",2));
      oos.close();
  }
  
  /**
   * 从copy.txt文件中读取流并转换成employee对象
   * @throws Exception
   */
  private static void readObject()throws Exception {
      FileInputStream fis = new FileInputStream("C:\\Users\\fuzy\\Desktop\\copy.txt");
      ObjectInputStream ois = new ObjectInputStream(fis);
      Employee e1 = (Employee)ois.readObject();
      Employee e2 = (Employee)ois.readObject();
  
      e1.showInfo();
      e2.showInfo();
      ois.close();
  }
  ```

#### 文件IO

##### File

> `File file = new File("c:\\data\\input-file.txt");`

- 检测文件是否存在

  ```java
  boolean fileExists = file.exists();
  ```

- 文件长度

  ```java
  long length = file.length()
  ```

- 重命名或移动文件

  ```java
  boolean success = file.renameTo(new File("c:\\data\\new-file.txt"));
  ```

- 删除文件

  ```java
  boolean success = file.delete();
  ```

- 检测某个文件是路径还是文件

  ```java
  boolean isDirectory = file.isDirectory();
  ```

- 读取文件中的目录列表

  ```java
  //list()方法返回当前File对象指向的目录中所有文件与子目录的字符串名称(译者注：不会返回子目录下的文件及其子目录名称)
  String[] fileNames = file.list();
  //listFiles()方法返回当前File对象指向的目录中所有文件与子目录相关联的File对象(译者注：与list()方法类似，不会返回子目录下的文件及其子目录)
  File[] files = file.listFiles();
  ```



## NIO

### 概述

> NIO（Non-blocking I/O，在Java领域，也称为New I/O），是一种同步非阻塞的I/O模型。NIO有三大核心组件分别是`Channel`、`Buffer`和`Selector`，其他的组件（如`Pipr`、`FileLock`）只不过是与三个核心组件共同使用的工具类。

### NIO核心组件

#### Channel

##### 概述

`Channel`有点类似流，但又有些不同：

- 既可以从`Channel`中读取数据，又可以写数据到`Channel`，流的读写通常是单向的。
- `Channel`可以异步地读写。
- `Channel`中的数据总是要先读到一个`Buffer`，或者总是要从一个`Buffer`中写入。

如上面说所，`Channel`与`Buffer`的关系如下图所示：

![image-20211105204021267](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211105204021267.png)

##### Channel的类型

- `FileChannel`：从文件中读写数据

  ```java
  public static void main(String[] args)throws Exception {
      //创建RandomAccessFile对象 demo.txt文件内容是234341234344
      RandomAccessFile aFile = new RandomAccessFile("C:\\Users\\fuzy\\Desktop\\demo.txt","rw");
      //获取channel
      FileChannel channel = aFile.getChannel();
  
      //分配内存
      ByteBuffer buffer = ByteBuffer.allocate(5);
      //读取数据到buffer中
      int bytesRead = channel.read(buffer);
      while (bytesRead != -1) {
          //输出每次读取到的内容长度
          System.out.println("Read " + bytesRead);
          //注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
          buffer.flip();
  
          while(buffer.hasRemaining()){
              System.out.println((char) buffer.get());
          }
  
          buffer.clear();
          bytesRead = channel.read(buffer);
      }
      aFile.close();
  }
  ```

- `DatagramChannel`：能通过`UDP`读写网络中的数据

- `SocketChannel`：能通过`TCP`读写网络中的数据

- `ServerSocketChannel`：可以监听新进来的`TCP`连接，像`Web`服务器那样。对每一个新进来的连接都会创建一个`SocketChannel`

#### Buffer

##### 概述

> `Java NIO`中的`Buffer`用于和NIO通道进行交互。缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成`NIO Buffer`对象，并提供了一组方法，用来方便的访问该块内存。

##### Buffer的基本用法

我们先来看一下如下代码：

```java
public static void main(String[] args)throws Exception {
    //创建RandomAccessFile对象 demo.txt文件内容是234341234344
    RandomAccessFile aFile = new RandomAccessFile("C:\\Users\\fuzy\\Desktop\\demo.txt","rw");
    //获取channel
    FileChannel channel = aFile.getChannel();

    //分配内存
    ByteBuffer buffer = ByteBuffer.allocate(5);
    //读取数据到buffer中
    int bytesRead = channel.read(buffer);
    while (bytesRead != -1) {
        //输出每次读取到的内容长度
        System.out.println("Read " + bytesRead);
        //注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
        buffer.flip();
		//buffer中存在至少一个数据数据就为真
        while(buffer.hasRemaining()){
            //buffer.get():缓冲区当前位置的字节
            System.out.println((char) buffer.get());
        }

        buffer.clear();
        bytesRead = channel.read(buffer);
    }
    aFile.close();
}
```

如上所示，使用Buffer读写数据一般遵循以下四个步骤：

1. 写入数据到Buffer
2. 调用`flip()`方法
3. 从Buffer中读取数据
4. 调用`clear()`方法或者`compact()`方法

当向`buffer`写入数据（`channel.read(buffer)`）时，`buffer`会记录下写了多少数据。**一旦要读取数据，需要通过`flip()`方法将`Buffer`从写模式切换到读模式。**在读模式下，可以读取之前写入到`buffer`的所有数据。

一旦读完了`Buffer`长度中的所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用`clear()`或`compact()`方法。`clear()`方法会清空整个缓冲区。`compact()`方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

##### Buffer读写数据的本质

![image-20211105211304539](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211105211304539.png)

​																			**Buffer读写工作示意图**

由上图所示，`Buffer`在进行读或者写时，内容维护了三个属性：

- `capacity`

  内存块的大小，在读或者写模式下含义一样。

- `position`

  **写模式下，`position`初始值为0，当数据写到`Buffer`后，`position`会向前移动到下一个可插入数据的`Buffer`单元。`position`最大可为`capacity` – 1。**

  **读模式下，当将Buffer从写模式切换到读模式，`position`会被重置为0. 当从`Buffer`的`position`处读取数据时，`position`向前移动到下一个可读的位置。**

  总而言之，`position`就是记录读写的位置，下一次读或者写时，会从当前位置开始。写切换到读调用的函数是`flip()`。

- `limit`

  写模式下，`Buffer`的`limit`表示你最多能往`Buffer`里写多少数据，即`limit`==`capacity`。

  读模式下，`limit`表示你最多能读到多少数据，即在写模式下的`position`大小。

##### Buffer的类型

- `ByteBuffer`
- `MappedByteBuffer`
- `CharBuffer`
- `DoubleBuffer`
- `FloatBuffer`
- `IntBuffer`
- `LongBuffer`
- `ShortBuffer`

除了`MappedByteBuffer`以外，其他的类型则是通过对应的类型来操作缓冲区。下面以`ByteBuffer`为例，展示相关的操作。

**ByteBuffer**

- 内存分配

  ```java
  ByteBuffer buf = ByteBuffer.allocate(48);
  ```

- 向`Buffer`中写数据

  1. 从`Channel`写到`Buffer`

     ```java
     int bytesRead = inChannel.read(buf);
     ```

  2. 通过`Buffer`的`put()`方法写到`Buffer`里

     ```java
     buf.put(127);
     ```

- 写模式切换到读模式

  ```java
  buffer.flip();
  ```

- 读取数据

  1. 从`Buffer`读取数据到`Channel`

     ```java
     int bytesWritten = inChannel.write(buf);
     ```

  2. `get()`

     ```java
     //调用get方法后，position会移动到下一个可读的位置
     byte aByte = buf.get();
     ```

- `rewind()`方法

  `Buffer.rewind()`将`position`设回0，所以你可以重读`Buffer中`的所有数据。`limit`保持不变，仍然表示能从`Buffer`中读取多少个元素（byte、char等）。

- `clear()`

  `position`将被设回0，`limit`被设置成 capacity的值。也就是`Buffer`中的数据未被清除，只是对应的标记被重置。如果在调用该方法之前`Buffer`还有没被读到的数据，这些数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。

- `compact()`

  如果`Buffer`中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用`compact()`方法。

  `compact()`方法将所有未读的数据拷贝到`Buffer`起始处。然后将`position`设到最后一个未读元素正后面。`limit`属性依然像`clear()`方法一样，设置成`capacity`。现在`Buffer`准备好写数据了，但是不会覆盖未读的数据。

- `mark()`和`reset()`方法

  通过调用`Buffer.mark()`方法，可以标记`Buffer`中的一个特定`position`。之后可以通过调用`Buffer.reset()`方法恢复到这个`position`。

#### Selector

##### 概述

`Selector`（选择器）是`Java NIO`中能够检测一到多个`NIO`通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个`channel`，从而管理多个网络连接，如下图：

![image-20211105220344780](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211105220344780.png)

##### 为什么使用Selector

​	以前`cpu`内核还没有很多个的时候，假设就是单核那么对于多线程之间的切换是昂贵的，并且每个线程也占用操作系统中的一些资源（内存）。因此，使用的线程越少越好。

##### Selector的用法

- 创建`Selector`

  ```java
  Selector selector = Selector.open();
  ```

- 注册`Channel`

  > 注意：与`Selector`一起使用时，`Channel`必须处于非阻塞模式下。`Socket channels`可以正常使用，但是`FileChannel`无法切换到非阻塞模式。

  ```java
  channel.configureBlocking(false);
  SelectionKey key = channel.register(selector,Selectionkey.OP_READ);
  ```

  `channel.register(selector,Selectionkey.OP_READ)`第二个参数是代表`Selector`可以监听`Channel`哪些事件。**Channel有以下四种事件可以被监听：**

  - `Connnect`：`SelectionKey.OP_CONNECT`
  - `Accept`：`SelectionKey.OP_ACCEPT`
  - `Read`：`SelectionKey.OP_READ`
  - `Write`：`SelectionKey.OP_WRITE`

  如果想对多种事情进行监听，可以使用位操作符来进行连接，如下：

  ```java
  int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
  ```

- `SelectionKey`对象

  ![image-20211106172912547](https://image-1301573777.cos.ap-chengdu.myqcloud.com/image-20211106172912547.png)

  - `interestOps`

    监听的事件类型集合，通过位操作符表示。

  - `ready`

    `ready`集合是代表监听`channel`已经准备就绪的操作的集合，通常可以使用以下四种方法来表示：

    ```java
    selectionKey.isAcceptable();
    selectionKey.isConnectable();
    selectionKey.isReadable();
    selectionKey.isWritable();
    ```

  - `selector`

    ```java
    //通过`SelectionKey`获取Selector
    Selector selector = selectionKey.selector();
    ```

  - `channel`

    ```java
    //通过`SelectionKey`获取channel
    Channel  channel  = selectionKey.channel();
    ```

  - `attachment`

    > 可以将一个对象或者更多信息附着到SelectionKey上，这样就能方便的识别某个给定的`channel`

    ```java
    //类似与map；调用该方法后相当于加到对应的map中，一次只能附加一个对象； 调用此方法会导致丢弃任何先前的附加对象。 可以通过附加null来丢弃当前对象。稍后可以通过attachment方法检索附加的对象。 
    selectionKey.attach(theObject);
    Object attachedObj = selectionKey.attachment();
    
    //第二种方式
    SelectionKey key = channel.register(selector,SelectionKey.OP_READ,buffer);
    ```

##### Select选择Channel

​	一旦向`Selector`注册了一或多个通道，就可以调用几个重载的`select()`方法。这些方法返回你所感兴趣的事件（如连接、接受、读或写）已经准备就绪的那些通道。换句话说，如果你对“读就绪”的通道感兴趣，`select()`方法会返回读事件已经就绪的那些通道。`select`方法返回的就是上次调用`select()`方法后有多少通道变成就绪状态。

- `int select()`

  `select()`阻塞到至少有一个通道在你注册的事件上就绪了。

- `int select(long timeout)`

  和`select()`一样，只不过阻塞会有timeout毫秒限制。

- `selectNow()`

  不会阻塞，不管什么通道就绪都立刻返回。

##### SelectedKeys方法

​	一旦调用了`select()`方法，并且返回值表明有一个或更多个`Channel`就绪了，然后可以通过调用`selector`的`selectedKeys`()方法，访问“已选择键集（selected key set）”中的就绪`Channel`。如下所示：

```java
Set selectedKeys = selector.selectedKeys();
```

​	当像`Selector`注册`Channel`时，`Channel.register()`方法会返回一个`SelectionKey`对象。这个对象代表了注册到该`Selector`的通道。可以通过`SelectionKey`的`selectedKeySet()`方法访问这些对象。可以遍历这个已选择的键集合来访问就绪的通道。如下：

```java
Set selectedKeys = selector.selectedKeys();
Iterator keyIterator = selectedKeys.iterator();
while (keyIterator.hasNext()) {
    SelectionKey key = (SelectionKey) keyIterator.next();
    if (key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    //Selector不会自己从已选择键集中移除SelectionKey实例。必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
    keyIterator.remove();
}
```

##### Selector代码示例

```java
public static void main(String[] args) throws Exception {
    ServerSocket socket = new ServerSocket(8080);
    ServerSocketChannel channel = socket.getChannel();
    //获取selector
    Selector selector = Selector.open();
    //设置位非阻塞连接
    channel.configureBlocking(false);
    //注册读事件
    SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
    while (true){
        //获取已准备好的事件个数
        int readyChannels = selector.select();
        //当readyChannels等于0时，继续循环
        if(readyChannels==0){
            continue;
        }
        //获取就绪的channel集合
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            if(key.isAcceptable()) {
                // a connection was accepted by a ServerSocketChannel.
            } else if (key.isConnectable()) {
                // a connection was established with a remote server.
            } else if (key.isReadable()) {
                // a channel is ready for reading
            } else if (key.isWritable()) {
                // a channel is ready for writing
            }
            //移除已处理的channel
            iterator.remove();

        }
    }
}
```

#### SocketChannel

> `Java NIO`中的`SocketChannel`是一个连接到TCP网络套接字的`channel`。

##### 打开SocketChannel

```java
//打开一个SocketChannel并连接到互联网上的某台服务器。
SocketChannel socketChannel = SocketChannel.open();
socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
```

##### 非阻塞模式

如果`SocketChannel`在非阻塞模式下，此时调用`connect()`，该方法可能在连接建立之前就返回了。为了确定连接是否建立，可以调用`finishConnect()`的方法。如下代码所示：

```java
socketChannel.configureBlocking(false);
socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

while(!socketChannel.finishConnect() ){
    //wait, or do something else...
}

```

##### 读写

```java
SocketChannel socketChannel = SocketChannel.open(
new InetSocketAddress("www.baidu.com", 80));
//设置成非阻塞模式
socketChannel.configureBlocking(false);
ByteBuffer byteBuffer = ByteBuffer.allocate(16);
//将数据读到缓冲区
socketChannel.read(byteBuffer);
socketChannel.close();
```

#### ServerSocketChannel

> `ServerSocketChannel`是一个可以监听新进来的TCP连接的通道。

```java
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.socket().bind(new InetSocketAddress(9999));
//设置成非阻塞模式
serverSocketChannel.configureBlocking(false);

while(true){
    //监听连接；在非阻塞模式下，accept()方法会立即返回，如果没有新进来的连接，返回null
    SocketChannel socketChannel = serverSocketChannel.accept();
    if(socketChannel != null){
        //do something with socketChannel...
    }
}
```


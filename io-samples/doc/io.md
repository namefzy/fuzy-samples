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

#### 文件IO

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

    

  

#### 管道IO

> Java IO中的管道为运行在同一个JVM中的两个线程提供了通信的能力，你不能利用管道与不同的JVM中的线程通信(不同的进程)。

- `PipedInputStream`和`PipedOutputStream`
- `PipedReader`和`PipedWriter`

## NIO


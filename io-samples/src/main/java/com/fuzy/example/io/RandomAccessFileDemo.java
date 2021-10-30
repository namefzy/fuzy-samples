package com.fuzy.example.io;

import java.io.*;

/**
 * @author fuzy
 * @date 2021/10/30 21:54
 */
public class RandomAccessFileDemo {

    public static void main(String[] args)throws Exception {

        String filePath="C:\\Users\\fuzy\\Desktop\\data.txt";
        insert(filePath,3,"插入指定位置指定内容");


    }

    private static void read() throws IOException {
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
}

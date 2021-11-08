package com.fuzy.example.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author fuzy
 * @date 2021/11/5 20:45
 */
public class FileChannelExample {

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
}

package com.fuzy.example.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;


/**
 * @author fuzy
 * @date 2021/10/16 15:56
 */
public class NioExample {

    @Test
    public void ByteBufferTest() throws Exception {
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\fuzy\\Desktop\\data.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip();//转换成读模式
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());//一次读取一个字节
            }
            buffer.clear();
            read = channel.read(buffer);
        }
        file.close();
    }

    /**
     * Scattering Reads是指数据从一个channel读取到多个buffer中
     */
    @Test
    public void scatterTest() throws Exception {
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = {header, body};
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\fuzy\\Desktop\\data.txt", "rw");
        FileChannel channel = file.getChannel();
        channel.read(bufferArray);
    }

    /**
     * Gathering ：Gathering Writes是指数据从多个buffer写入到同一个channel
     */
    @Test
    public void gatheringTest() throws Exception {
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = {header, body};
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\fuzy\\Desktop\\data.txt", "rw");
        FileChannel channel = file.getChannel();
        channel.write(bufferArray);
    }

    @Test
    public void transferFrom() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel,position, count);
    }

    @Test
    public void transferTo()throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);

    }
}

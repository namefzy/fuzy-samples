package com.fuzy.example.io;


import java.io.*;

/**
 * @author fuzy
 * @date 2021/10/29 22:02
 */
public class FileInputStreamDemo {

    public static void main(String[] args) throws Exception{
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\fuzy\\Desktop\\data.txt"));
        FileOutputStream fos = new FileOutputStream("C:\\Users\\fuzy\\Desktop\\copy.txt");
        try{
//            readSingleByte(fis,fos);
            readAllDataByByteArray(fis,fos);
//            readDatabaseByte(fis,fos);
        }catch (Exception e){
            fis.close();
            fos.close();
        }
    }

    /**
     *
     * @param fis
     * @param fos
     */
    public static void readDatabaseByte(FileInputStream fis, FileOutputStream fos)throws Exception {
        byte[] bytes = new byte[3];
        int len = 0;
        while ((len=fis.read(bytes,0,2))!=-1){
            fos.write(bytes,0,len);
            System.out.println(new String(bytes,0,len));
            System.out.println("============");
        }
    }

    /**
     * 一次读取完文本数据
     * @param fis
     * @param fos
     * @throws IOException
     */
    public static void readAllDataByByteArray(InputStream fis, FileOutputStream fos) throws IOException {
        //fis.available()这个方法可以在读写操作前先得知数据流里有多少个字节可以读取；在文本操作中不会发生问题；用于网络操作会出现问题
        //bytes将流中读取的数据存储在字节数组中
        byte[] bytes = new byte[3];
        int len = 0;
        while ((len=fis.read(bytes))!=-1){
            fos.write(bytes);
            System.out.println(new String(bytes,0,len));
            System.out.println("============");
        }
    }

    /**
     * 一次读取一个字节
     * @param fis
     * @param fos
     * @throws IOException
     */
    public static void readSingleByte(InputStream fis, FileOutputStream fos) throws IOException {
        int data = fis.read();
        while (data!=-1){
            System.out.println((char) data);
            fos.write(data);
            data = fis.read();
            System.out.println("============");
        }
    }

}

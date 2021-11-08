package com.fuzy.example.io;

import java.io.*;

/**
 * @author fuzy
 * @date 2021/11/1 22:08
 */
public class DataIOExample {
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
}

package com.fuzy.example.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author fuzy
 * @date 2021/10/31 20:30
 */
public class BufferedIOExample {
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
}

package com.fuzy.example.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author fuzy
 * @date 2021/10/31 19:56
 */
public class ByteArrayExample {

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
}

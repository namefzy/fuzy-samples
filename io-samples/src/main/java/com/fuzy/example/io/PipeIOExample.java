package com.fuzy.example.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author fuzy
 * @date 2021/10/31 19:39
 */
public class PipeIOExample {

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
}

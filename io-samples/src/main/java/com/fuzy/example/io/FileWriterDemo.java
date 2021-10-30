package com.fuzy.example.io;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author fuzy
 * @date 2021/10/30 21:17
 */
public class FileWriterDemo {
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
}

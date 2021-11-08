package com.fuzy.example.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author fuzy
 * @date 2021/11/1 22:26
 */
public class SerializableIOExample {

    public static void main(String[] args)throws Exception {
//        writeObject();
        readObject();
    }

    /**
     * 将对象序列化到copy.txt文件中
     * @throws Exception
     */
    private static void writeObject()throws Exception {
        FileOutputStream fos = new FileOutputStream("C:\\Users\\fuzy\\Desktop\\copy.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(new Employee("张三",1));
        oos.writeObject(new Employee("李四",2));
        oos.close();
    }

    /**
     * 从copy.txt文件中读取流并转换成employee对象
     * @throws Exception
     */
    private static void readObject()throws Exception {
        FileInputStream fis = new FileInputStream("C:\\Users\\fuzy\\Desktop\\copy.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Employee e1 = (Employee)ois.readObject();
        Employee e2 = (Employee)ois.readObject();

        e1.showInfo();
        e2.showInfo();
        ois.close();
    }


}

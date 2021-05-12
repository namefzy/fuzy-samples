package com.fuzy.example.proxy.dynamic;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/10 22:36
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        //代理的真实对象
        Subject realSubject = new RealSubject();


//        ClassLoader loader = Subject.class.getClassLoader();
//        Class<?>[] interfaces = Subject.class.getInterfaces();
        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();
        InvocationHandler invocationHandler = new ProxySubject(realSubject);
        Subject subject = (Subject) Proxy.newProxyInstance(loader,interfaces,invocationHandler);
        subject.SayHello("校长");
        // 将生成的字节码保存到本地，
//        createProxyClassFile();
    }

        private static void createProxyClassFile(){
            String name = "ProxySubject";
            byte[] data = ProxyGenerator.generateProxyClass(name,new Class[]{Subject.class});
            FileOutputStream out =null;
            try {
                out = new FileOutputStream(name+".class");
                System.out.println((new File("hello")).getAbsolutePath());
                out.write(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null!=out) try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}

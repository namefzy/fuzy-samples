package com.fuzy.example;

/**
 * @ClassName Person
 * @Description TODO
 * @Author fuzy
 * @Date 2021/1/23 21:44
 * @Version 1.0
 */
public class Person{
    private final String name="Jack";
    private static final int age =0;
    private final double salary=100;
    private static String address;
    private final static String hobby="Programming";
    private static Object obj=new Object();
    public void say(){
        System.out.println("person say...");
    }

    public static int calc(int op1,int op2){

        op1=3;
        int result=op1+op2;
        Object obj=new Object();
        return result;
    }
    public static void main(String[] args){
        calc(1,2);
    }
}

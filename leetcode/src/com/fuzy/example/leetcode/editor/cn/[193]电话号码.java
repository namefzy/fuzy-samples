package com.fuzy.example.leetcode.editor.cn;

import java.io.*;

/**
 * @ClassName Solution17
 * @Description TODO
 * @Author 11564
 * @Date 2020/8/8 23:23
 * @Version 1.0.0
 */
class Solution18 {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\11564\\Desktop\\file.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str;
        while ((str=bufferedReader.readLine())!=null){
            System.out.println(str);
        }
        bufferedReader.close();
    }
}

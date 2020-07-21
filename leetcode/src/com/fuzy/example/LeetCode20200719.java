package com.fuzy.example;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LeetCode20200719
 * @Description 20200719算法题
 * @Author 11564
 * @Date 2020/7/19 13:09
 * @Version 1.0.0
 */
public class LeetCode20200719 {
    private static Map<String,Integer> map = new HashMap<>();
    private static Map<String,Integer> composeMap = new HashMap<>();
    static {
        map.put("I",1);
        map.put("V",5);
        map.put("X",10);
        map.put("L",50);
        map.put("C",100);
        map.put("D",500);
        map.put("M",1000);
        composeMap.put("IV",4);
        composeMap.put("IX",9);
        composeMap.put("XL",40);
        composeMap.put("XC",90);
        composeMap.put("CD",400);
        composeMap.put("CM",900);
    }
    public static void main(String[] args) {
        System.out.println(romanToInt1("MCMXCIV"));
    }

    public static int removeDuplicates(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        int p = 0;
        int q = 0;
        while (q<nums.length){
            if(nums[p]!=nums[q]){
                nums[p+1] = nums[q];
                p++;
            }
            q++;
        }

        return p+1;
    }
    /**
     * 转换罗马字符串
     * 官方解法
     */
    public static int romanToInt2(String s){
        Map<String,Integer> cusMap = new HashMap<>();
        cusMap.put("I",1);
        cusMap.put("V",5);
        cusMap.put("X",10);
        cusMap.put("L",50);
        cusMap.put("C",100);
        cusMap.put("D",500);
        cusMap.put("M",1000);
        cusMap.put("IV",4);
        cusMap.put("IX",9);
        cusMap.put("XL",40);
        cusMap.put("XC",90);
        cusMap.put("CD",400);
        cusMap.put("CM",900);

        int result = 0;
        for (int i = 0; i < s.length();) {
            if(i+1<s.length()&&cusMap.containsKey(s.substring(i,i+2))){
                result+=cusMap.get(s.substring(i,i+2));
                i+=2;
            }else{
                result+=cusMap.get(s.substring(i,i+1));
                i+=1;
            }
        }
        return result;
    }
    /**
     * 转换罗马字符串
     * 耗时耗空间解法
     * @param s
     * @return
     */
    public static int romanToInt1(String s){
        Integer result = 0;

        for (Map.Entry<String, Integer> entry : composeMap.entrySet()) {
            if(s.contains(entry.getKey())){
                result +=entry.getValue();
                s = s.replaceAll(entry.getKey(),"");
            }
        }
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if(map.containsKey(String.valueOf(aChar))){
                result +=map.get(String.valueOf(aChar));
            }
        }

        return result;
    }

}

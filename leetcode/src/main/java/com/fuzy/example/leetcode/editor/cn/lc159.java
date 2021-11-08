package com.fuzy.example.leetcode.editor.cn;

import java.util.HashSet;
import java.util.Set;

/**
 *给定一个字符串 s ，找出 至多 包含两个不同字符的最长子串 t 。
 * 例 1:
 *
 * 输入: “eceba”
 *
 * 输出: 3
 *
 * 解释: t 是 “ece”，长度为3。
 *
 * 例 2:
 *
 * 输入: “ccaabbb”
 *
 * 输出: 5
 *
 * 解释: t 是 “aabbb”，长度为5。
 */
public class lc159 {

    public static void main(String[] args) {

        int length = lengthOfLongestSubstringTwoDistinct1("ccaabbb");
        System.out.println(length);

    }

    public static int lengthOfLongestSubstringTwoDistinct1(String s){
        int[] windows = new int[128];
        int start = 0,end=0,maxLen=0,count=0;
        while (end<s.length()){
            if(windows[s.charAt(end)]==0){
                count++;
            }
            windows[s.charAt(end)]++;
            end+=1;
            //当count=3时，说明该字符串中包含2个以上字符
            while (count>2){
                char left = s.charAt(start);
                //字符个数为1时，则count-1,求出字符的长度
                if(windows[left]==1){
                    count-=1;
                }
                windows[left]-=1;
                start+=1;
            }
            maxLen = Math.max(maxLen,end-start);
        }
        return maxLen;
    }
    public int lengthOfLongestSubstringTwoDistinct(String s){

        Set<Character> set = new HashSet<>();
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            set.clear();
            int count = 0;
            for (int j = i; j < s.length(); j++) {
                if(set.contains(s.charAt(j))){
                    count++;
                    res = Math.max(res,count);
                }else if(set.size()<2){
                    set.add(s.charAt(j));
                    count++;
                    res = Math.max(res,count);
                }else{
                    break;
                }
            }
        }
        return res;


    }
}

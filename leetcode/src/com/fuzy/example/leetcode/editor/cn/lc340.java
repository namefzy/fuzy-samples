package com.fuzy.example.leetcode.editor.cn;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/2/25 12:46
 */
public class lc340 {
    public int lengthOfLongestSubstringKDistinct(String s,int k){
        int[] lookup = new int[128];
        int left = 0,right = 0,count = 0,maxLen = 0;
        while (right<s.length()){
            char r = s.charAt(right);
            if(lookup[r]==0){
                count++;
            }
            lookup[r]++;
            right++;
            while (count>k){
                char l = s.charAt(left);
                if(lookup[l]==1){
                    count--;
                }
                lookup[l]--;
                left++;
            }
            maxLen = Math.max(maxLen,right-left);
        }
        return maxLen;
    }
}

package com.fuzy.example.leetcode.editor.cn;
//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
// 示例 1: 
//
// 输入: "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
// Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 4486 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution68 {

    public int lengthOfLongestSubstring1(String s){
        if(s.length()==0){
            return 0;
        }
        Map<Character,Integer> map = new HashMap<>();
        int left = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);
        }
        return max;
    }

    public int lengthOfLongestSubstring(String s) {
        if(s.length()==0){
            return 0;
        }
        Map<Character,Integer> map = new HashMap<>();
        int left = 0;
        int length = s.length();
        int max = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            //当遇到重复的字符时，left = i+1  "a-0 b-1 b-2 a-3"
            if(map.containsKey(c)){
               left =Math.max(left,map.get(c)+1);
            }
            map.put(c,i);
            max = Math.max(max,i-left+1);
        }

        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

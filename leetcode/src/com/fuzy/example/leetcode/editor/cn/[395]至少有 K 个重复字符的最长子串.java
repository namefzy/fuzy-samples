package com.fuzy.example.leetcode.editor.cn;
//给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
//
// 
//
// 示例 1： 
//
// 
//输入：s = "aaabb", k = 3
//输出：3
//解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
// 
//
// 示例 2： 
//
// 
//输入：s = "ababbc", k = 2
//输出：5
//解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 104 
// s 仅由小写英文字母组成 
// 1 <= k <= 105 
// 
// Related Topics 递归 分治算法 Sliding Window 
// 👍 390 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution395 {
    public int longestSubstring(String s, int k) {
        if(s.length()==0){
            return 0;
        }

        Map<Character,Integer> counter = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            counter.put(s.charAt(i),counter.getOrDefault(s.charAt(i),0)+1);
        }
        for (Character c : counter.keySet()) {
            if(counter.get(c)<k){
                int res = 0;
                String[] split = s.split(String.valueOf(c));
                for (String t : split) {
                    res = Math.max(res,longestSubstring(t,k));
                }
                return res;
            }
        }
        return s.length();
    }

    public static void main(String[] args) {

        String s= "abcad";
        String[] cs = s.split("c");
        for (String c : cs) {
            System.out.println(String.valueOf(c));
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

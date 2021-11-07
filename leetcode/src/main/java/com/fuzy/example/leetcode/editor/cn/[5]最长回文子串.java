package com.fuzy.example.leetcode.editor.cn;
//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1： 
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
// 
//
// 示例 2： 
//
// 输入: "cbbd"
//输出: "bb"
// 
// Related Topics 字符串 动态规划 
// 👍 2825 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution69 {
    public String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int length = s.length();
        int begin = 0;
        int maxLength = 1;
        // i=length-1，已经是最后一位了
        for (int i = 0; i < length-1; i++) {
            for (int j = i+1; j < length; j++) {
                if(j-i+1>maxLength&&isPalindrome(chars,i,j)){
                    maxLength = j-i+1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+maxLength);
    }

    /**
     * 判断是否是回文串
     * @param chars
     * @param i
     * @param j
     * @return
     */
    public boolean isPalindrome(char[] chars,int i,int j){
        while (i<j){
            if(chars[i]!=chars[j]){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

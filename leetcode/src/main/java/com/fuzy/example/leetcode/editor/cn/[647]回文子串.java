package com.fuzy.example.leetcode.editor.cn;//给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
//
// 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。 
//
// 
//
// 示例 1： 
//
// 输入："abc"
//输出：3
//解释：三个回文子串: "a", "b", "c"
// 
//
// 示例 2： 
//
// 输入："aaa"
//输出：6
//解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa" 
//
// 
//
// 提示： 
//
// 
// 输入的字符串长度不会超过 1000 。 
// 
// Related Topics 字符串 动态规划 
// 👍 569 👎 0



//leetcode submit region begin(Prohibit modification and deletion)
class Solution647 {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append('a');
        System.out.println(countSubstrings("abba"));
    }

    /**
     * 对于dp[i][j]是否是二维数组取决于dp[i+1][j-1]是否是二维数组；如果dp[i+1][j-1]是回文串则判断chatAt[i]和chatAt[j]是否相等
     * @param s
     * @return
     */
    public int countSubstrings1(String s){
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        int count =0;
        for (int i = length-1; i>=0; i--) {
            for (int j = i; j < length; j++) {
                if(s.charAt(i)!=s.charAt(j)){
                    continue;
                }
                dp[i][j] = j-i<=2||dp[i+1][j-1];
                if(dp[i][j]){
                    count++;
                }
            }
        }
        return  count;
    }

    public static int countSubstrings(String s) {
        char[] chars = s.toCharArray();
        int count = chars.length;
        for (int i = 0; i < chars.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(chars[i]);
            for (int j = i+1; j < chars.length; j++) {
                sb.append(chars[j]);
                if(isPalindrome(sb.toString())){
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 判断是否是回文子串
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s){
        int left = 0;
        int right = s.length()-1;
        while(left<right){
            char c = s.charAt(left);
            char c1 = s.charAt(right);
            if(c!=c1){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

package com.fuzy.example.leetcode.editor.cn;
 //给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
//
// 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。 
//
// 注意: 
//假设字符串的长度不会超过 1010。 
//
// 示例 1: 
//
// 
//输入:
//"abccccdd"
//
//输出:
//7
//
//解释:
//我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
// 
// Related Topics 哈希表 
// 👍 218 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution23 {
    public int longestPalindrome(String s) {
        int[] count = new int[128];
        //统计每个位置对应的字符串出现几次
        for (char c:s.toCharArray()){
            count[c]++;
        }
        int ans = 0;
        for (int v : count) {
            //乘以2的长度
            ans += v / 2 * 2;
            //这里只执行一次
            if (v % 2 == 1 && ans % 2 == 0){
                ans++;
            }


        }
        return ans;

    }

    public static void main(String[] args) {
        String s = "abdadsfslf";
        int[] count = new int[128];
        for (char c: s.toCharArray())
            count[c]++;

        for (int i : count) {
            System.out.println(i);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

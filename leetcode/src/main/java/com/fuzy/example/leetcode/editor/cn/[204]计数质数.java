package com.fuzy.example.leetcode.editor.cn;
//统计所有小于非负整数 n 的质数的数量。
//
// 示例: 
//
// 输入: 10
//输出: 4
//解释: 小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
// 
// Related Topics 哈希表 数学 
// 👍 425 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution40 {
    public static void main(String[] args) {
        boolean[] isPrim = new boolean[10];
        Arrays.fill(isPrim,true);
        for (boolean b : isPrim) {
            System.out.println(b);
        }
    }
    public int countPrimes(int n) {
        boolean[] isPrim = new boolean[n];
        Arrays.fill(isPrim, true);
        for (int i = 2; i * i < n; i++)
            if (isPrim[i])
                for (int j = i * i; j < n; j += i)
                    isPrim[j] = false;

        int count = 0;
        for (int i = 2; i < n; i++)
            if (isPrim[i]) count++;

        return count;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

package com.fuzy.example.leetcode.editor.cn;
//给你一个整数数组 perm ，它是前 n 个正整数的排列，且 n 是个 奇数 。
//
// 它被加密成另一个长度为 n - 1 的整数数组 encoded ，满足 encoded[i] = perm[i] XOR perm[i + 1] 。比方说
//，如果 perm = [1,3,2] ，那么 encoded = [2,1] 。 
//
// 给你 encoded 数组，请你返回原始数组 perm 。题目保证答案存在且唯一。 
//
// 
//
// 示例 1： 
//
// 输入：encoded = [3,1]
//输出：[1,2,3]
//解释：如果 perm = [1,2,3] ，那么 encoded = [1 XOR 2,2 XOR 3] = [3,1]
// 
//
// 示例 2： 
//
// 输入：encoded = [6,5,4,6]
// 2^4 4^1 1^5 5^3
//输出：[2,4,1,5,3]
// 
//
// 
//
// 提示：
// 11 101 110 111 1001
// [2,5,6,7,9]
// 
// 3 <= n < 105 
// n 是奇数。 
// encoded.length == n - 1 
// 
// Related Topics 位运算 
// 👍 70 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1734{

    /**
     * 注意 前n个正整数 代表原数组是1-n组成
     * @param encoded
     * @return
     */
    public  int[] decode(int[] encoded) {
        int length = encoded.length + 1;
        int allXor = 0;
        //把1到n中间的所有数字都异或一遍
        for (int i = 1; i <= length; i++) {
            allXor ^= i;
        }

        int even = 0;
        //数组encoded中第偶数个元素都异或一遍，其实就是原数组1-n的所有元素异或的结果
        for (int i = 1; i < length - 1; i += 2) {
            even ^= encoded[i];
        }

        int[] res = new int[length];
        //求出第一个值，后面就简单了
        res[0] = allXor ^ even;
        for (int i = 0; i < length - 1; i++) {
            res[i + 1] = res[i] ^ encoded[i];
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

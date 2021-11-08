package com.fuzy.example.leetcode.editor.cn;//未知 整数数组 arr 由 n 个非负整数组成。
//
// 经编码后变为长度为 n - 1 的另一个整数数组 encoded ，其中 encoded[i] = arr[i] XOR arr[i + 1] 。例如，a
//rr = [1,0,2,1] 经编码后得到 encoded = [1,2,3] 。 
//
// 给你编码后的数组 encoded 和原数组 arr 的第一个元素 first（arr[0]）。 
//
// 请解码返回原数组 arr 。可以证明答案存在并且是唯一的。 
//
// 
//
// 示例 1： 
//
// 
//输入：encoded = [1,2,3], first = 1
//输出：[1,0,2,1]
//解释：若 arr = [1,0,2,1] ，那么 first = 1 且 encoded = [1 XOR 0, 0 XOR 2, 2 XOR 1] = [
//1,2,3]
// 
//
// 示例 2： 
//
// 
//输入：encoded = [6,2,7,3], first = 4
//输出：[4,2,0,7,4]
// 
//
// 
//
// 提示： 
//
// 
// 2 <= n <= 104 
// encoded.length == n - 1 
// 0 <= encoded[i] <= 105 
// 0 <= first <= 105 
// 
// Related Topics 位运算 
// 👍 56 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution1720 {
    /**
     *  nums[i]^
     * @param encoded
     * @param first
     * @return
     */
    public int[] decode(int[] encoded, int first) {

        int[] beforeEncoded = new int[encoded.length+1];
        beforeEncoded[0] = first;

        for (int i = 1; i < encoded.length; i++) {

            beforeEncoded[i] = beforeEncoded[i-1]^encoded[i-1];
        }
        return beforeEncoded;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

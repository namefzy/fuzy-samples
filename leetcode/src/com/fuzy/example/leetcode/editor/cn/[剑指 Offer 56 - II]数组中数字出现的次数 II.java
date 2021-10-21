package com.fuzy.example.leetcode.editor.cn;//在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
//
// 
//
// 示例 1： 
//
// 输入：nums = [3,4,3,3]
//输出：4
// 
//
// 示例 2： 
//
// 输入：nums = [9,1,7,9,7,9,7]
//输出：1 
//
// 
//
// 限制： 
//
// 
// 1 <= nums.length <= 10000 
// 1 <= nums[i] < 2^31 
// 
//
// 
// Related Topics 位运算 数组 👍 240 👎 0

//9 1001
//7 0111
//1 0001
//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer56 {

    public static void main(String[] args) {
        singleNumber(new int[]{9,65,7,9,7,9,7});
    }

    public static int singleNumber(int[] nums) {
        int[] counts = new int[32];
        //给二进制每位累加值
        for(int num : nums) {
            for(int j = 0; j < 32; j++) {
                counts[j] += num & 1;
                num >>>= 1;
            }
        }
        //[1,3,3,4,4,0,1,0,0,0,0,0,0,0,0,0]
        //针对结果 1*2*2*2*2*2*2+1；
        int res = 0, m = 3;
        for(int i = 0; i < 32; i++) {
            //二进制右位移
            res <<= 1;
            res |= counts[31 - i] % m;
        }
        return res;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

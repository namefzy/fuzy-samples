package com.fuzy.example.leetcode.editor.cn;//给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
//
// 
//
// 进阶：你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？ 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,1,3,2,5]
//输出：[3,5]
//解释：[5, 3] 也是有效的答案。
// 
//
// 示例 2： 
//
// 
//输入：nums = [-1,0]
//输出：[-1,0]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0,1]
//输出：[1,0]
// 
//
// 提示： 
//
// 
// 2 <= nums.length <= 3 * 10⁴ 
// -2³¹ <= nums[i] <= 2³¹ - 1 
// 除两个只出现一次的整数外，nums 中的其他数字都出现两次 
// 
// Related Topics 位运算 数组 👍 486 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution260III {

    /**
     * 异或运算：
     * 任何数和 00 做异或运算，结果仍然是原来的数，即 a \oplus 0=aa⊕0=a。
     * 任何数和其自身做异或运算，结果是 00，即 a \oplus a=0a⊕a=0。
     * 异或运算满足交换律和结合律，
     * @param nums
     * @return
     */
    public int[] singleNumber(int[] nums) {
        int xorsum = 0;
        //进行异或运算异或运算规则：最后xorsum的是 x1和x2两个唯一出现数的异或值,显然结果xorsum不可能为0，如果为0,则x1和x2相等，不符合题意
        for (int num : nums) {
            xorsum ^=num;
        }

        //找出异或运算中最低位的那个1，假设其为i位;假设x1的i位为1，x2的i位位0
        int lsb = (xorsum==Integer.MAX_VALUE?xorsum:xorsum&(-xorsum));

        int x1=0,x2=0;
        for (int num : nums) {
            //数组中i位为1的所有数，进行异或运算，最后得出x1
            if((num&lsb)!=0){
                x1 ^=num;
            }else {
                //数组中i位为0的所有书，进行异或运算，最后得出x2；
                x2 ^=num;
            }
        }
        return new int[]{x1,x2};
    }
}
//leetcode submit region end(Prohibit modification and deletion)

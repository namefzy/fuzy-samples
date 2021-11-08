package com.fuzy.example.leetcode.editor.cn;//一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
//
// 
//
// 示例 1： 
//
// 输入：nums = [4,1,4,6]
//输出：[1,6] 或 [6,1]
// 
//
// 示例 2： 
//
// 输入：nums = [1,2,10,4,1,4,3,3]
//输出：[2,10] 或 [10,2] 
//
// 
//
// 限制： 
//
// 
// 2 <= nums.length <= 10000 
// 
//
// 
// Related Topics 位运算 数组 👍 476 👎 0


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer56 {

    public static void main(String[] args) {
        int[] ints = singleNumbers(new int[]{1, 2, 10, 4, 1, 4, 3, 3});
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

    public static int[] singleNumbers(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        int[] arrays = new int[2];
        for (int num : nums) {
            map.put(num,map.getOrDefault(num,0)+1);
        }
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue()==1){
               arrays[i++] = entry.getKey();
            }
        }
        return arrays;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

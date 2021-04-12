package com.fuzy.example.leetcode.editor.cn;
//给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
//
// 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [10,2]
//输出："210" 
//
// 示例 2： 
//
// 
//输入：nums = [3,30,34,5,9]
//输出："9534330"
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出："1"
// 
//
// 示例 4： 
//
// 
//输入：nums = [10]
//输出："10"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 109 
// 
// Related Topics 排序 
// 👍 604 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution279 {

    // 3 3O 330
    // 3 35 353
    public static void main(String[] args) {
        System.out.println(largestNumber(new int[]{3, 30, 34, 5, 9}));
    }

    /**
     * 数字不可拆
     *
     * @param nums
     * @return
     */
    public static String largestNumber(int[] nums) {
        int n = nums.length;
        // 转换成包装类型，以便传入 Comparator 对象（此处为 lambda 表达式）
        Integer[] numsArr = new Integer[n];
        for (int i = 0; i < n; i++) {
            numsArr[i] = nums[i];
        }

        Arrays.sort(numsArr, (x, y) -> {
            long sx = 10, sy = 10;
            while (sx <= x) {
                sx *= 10;
            }
            while (sy <= y) {
                sy *= 10;
            }
            return (int) (-sy * x - y + sx * y + x);
        });

        if (numsArr[0] == 0) {
            return "0";
        }
        StringBuilder ret = new StringBuilder();
        for (int num : numsArr) {
            ret.append(num);
        }
        return ret.toString();

    }

    /**
     * 3 3O 330 354 35554
     * 3 35 353
     *
     * @param arrays
     * @return
     */
    public static String[] sort(String[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            for (int j = i + 1; j < arrays.length; j++) {
                //找出最大的数字
                char[] chars = arrays[i].toCharArray();
                char[] chars1 = arrays[j].toCharArray();
                int length = Math.min(chars.length, chars1.length);
                for (int k = 0; k < length; k++) {
                    //如果chars1>chars，则交换
                    //如果chars 和chars1 位数不一致，则拿数据长度最大的和小的最后一位一次比较。
                    if (chars1[k] > chars[k]) {
                        String temp = arrays[i];
                        arrays[i] = arrays[j];
                        arrays[j] = temp;
                        break;
                    }
                }
            }
        }
        return null;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

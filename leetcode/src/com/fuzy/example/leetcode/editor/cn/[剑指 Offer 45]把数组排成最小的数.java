package com.fuzy.example.leetcode.editor.cn;//输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
//
// 
//
// 示例 1: 
//
// 输入: [10,2]
//输出: "102" 
//
// 示例 2: 
//
// 输入: [3,30,34,5,9]
//输出: "3033459" 
//
// 
//
// 提示: 
//
// 
// 0 < nums.length <= 100 
// 
//
// 说明: 
//
// 
// 输出结果可能非常大，所以你需要返回一个字符串而不是整数 
// 拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0 
// 
// Related Topics 贪心 字符串 排序 👍 283 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer45 {
    public static void main(String[] args) {
        minNumber(new int[]{6, 5, 3, 2, 4,8, 1, 9, 7});
    }

    public static String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        quickSort(strs, 0, strs.length - 1);
        Arrays.stream(strs).forEach(System.out::println);
        StringBuilder res = new StringBuilder();
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }

    public static void quickSort(String[] strs, int low, int high) {
        if (low < high) {
            int middle = getMiddle(strs, low, high);
            //计算左半部分
            quickSort(strs, low, middle - 1);
            //计算右半部分
            quickSort(strs, middle + 1, high);
        }
    }

    public static int getMiddle(String[] strs, int low, int high) {
        //数组的第一个数为基准元素
        String temp = strs[low];
        //     6                             6
        //{6, 5, 3, 2, 4,8, 1, 9, 7}->{1, 5, 3, 2, 4,8, 1, 9, 7}->{1, 5, 3, 2, 4,8, 8, 9, 7}->{1, 5, 3, 2, 4,6, 8, 9, 7}
        //这个循环做什么事情？
        while (low < high) {
            //从后向前找比基准小的数,确定所有比基准值大的数
            while (low < high && (strs[high] + temp).compareTo(temp + strs[high]) >= 0) {
                high--;
            }
            //把比基准小的数移到低端
            strs[low] = strs[high];
            //从前向后找比基准大的数
            while (low < high && (strs[low] + temp).compareTo(temp + strs[low]) <= 0) {
                low++;
            }
            //把比基准大的数移到高端
            strs[high] = strs[low];
        }
        //基准值替代的值 还原回去
        strs[low] = temp;
        return low;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

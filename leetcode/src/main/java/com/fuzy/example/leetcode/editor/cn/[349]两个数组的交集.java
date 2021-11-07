package com.fuzy.example.leetcode.editor.cn;
//给定两个数组，编写一个函数来计算它们的交集。
//
// 
//
// 示例 1： 
//
// 输入：nums1 = [1,2,2,1], nums2 = [2,2]
//输出：[2]
// 
//
// 示例 2： 
//
// 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//输出：[9,4] 
//
// 
//
// 说明： 
//
// 
// 输出结果中的每个元素一定是唯一的。 
// 我们可以不考虑输出结果的顺序。 
// 
// Related Topics 排序 哈希表 双指针 二分查找 
// 👍 237 👎 0


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution64 {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();
        for (int i : nums1) {
            set1.add(i);
        }
        HashSet<Integer> set2 = new HashSet<>();
        for (int i : nums2) {
            set2.add(i);
        }
        set1.retainAll(set2);
        int[] arrays = new int[set1.size()];
        int index = 0;
        for (Integer integer : set1) {
            arrays[index++] = integer;
        }
        return arrays;
    }

    public static void main(String[] args) {
        int j=0;
        int n = j++;
        System.out.println(n);
        System.out.println(++j);
        System.out.println(j);
        int i=0;
        System.out.println(++i);
        System.out.println(i++);
        System.out.println(i);

    }
}
//leetcode submit region end(Prohibit modification and deletion)

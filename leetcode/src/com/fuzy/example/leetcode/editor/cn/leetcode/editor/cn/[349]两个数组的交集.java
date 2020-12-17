package com.fuzy.example.leetcode.editor.cn.leetcode.editor.cn;
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
// 👍 301 👎 0


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution114 {

    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        int left = 0;
        int right = 0;
        while(left<nums1.length&&right<nums2.length){
            if(nums1[left]<nums2[right]){
                left++;
            }else if(nums1[left]>nums2[right]){
                right++;
            }else{
                if(!list.contains(nums1[left])){
                    list.add(nums1[left]);
                }
                left++;
                right++;
            }
        }
        int[] arrays = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arrays[i] = list.get(i);
        }
        return arrays;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

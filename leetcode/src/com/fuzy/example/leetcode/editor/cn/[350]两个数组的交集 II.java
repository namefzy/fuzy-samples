package com.fuzy.example.leetcode.editor.cn;
//给定两个数组，编写一个函数来计算它们的交集。
//
// 
//
// 示例 1： 
//
// 输入：nums1 = [1,1,2,2], nums2 = [2,2]
//输出：[2,2]
// 
//
// 示例 2: 
//
// 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//输出：[4,9] 
//
// 
//
// 说明： 
//
// 
// 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。 
// 我们可以不考虑输出结果的顺序。 
// 
//
// 进阶： 
//
//
// 如果给定的数组已经排好序呢？你将如何优化你的算法？ 
// 如果 nums1 的大小比 nums2 小很多，哪种方法更优？ 
// 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？ 
// 
// Related Topics 排序 哈希表 双指针 二分查找 
// 👍 407 👎 0


import java.util.Arrays;
import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution74 {
    public static void main(String[] args) {
        int[] nums1 = new  int[]{1,2,2,1};
        int[] nums2 = new  int[]{2,2};
        intersect(nums1,nums2);
    }

    /**
     * 双指针解法
     * @param nums1
     * @param nums2
     * @return
     */
    public  static int[] intersect1(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int index = 0 ;
        int i = 0;
        int j = 0;
        int[] arrays = new int[Math.min(nums1.length,nums2.length)];
        while (i<nums1.length&&j<nums2.length){
            if(nums1[i]==nums2[j]){
                arrays[index++] = nums1[i];
                i++;
                j++;
            }else if(nums1[i]>nums2[j]){
                j++;
            }else{
                i++;
            }
        }
        return Arrays.copyOfRange(arrays,0,index);
    }
    /**
     * HashMapK解法
     * @param nums1
     * @param nums2
     * @return
     */
    public  static int[] intersect(int[] nums1, int[] nums2) {

        HashMap<Integer,Integer> map = new HashMap<>(Math.min(nums1.length,nums2.length));
        if(nums1.length>nums2.length){
            return intersect(nums2,nums1);
        }
        for (int num : nums1) {
            Integer count = map.getOrDefault(num, 0);
            map.put(num,++count);
        }
        int[] arrays = new int[nums1.length];
        int index = 0;
        for (int i = 0; i < nums2.length; i++) {
            Integer orDefault = map.getOrDefault(nums2[i], 0);
            if(orDefault==0){
                map.remove(nums2[i]);
            }else if(map.containsKey(nums2[i])){
                arrays[index++] = nums2[i];
                map.put(nums2[i],--orDefault);
            }
        }
        return Arrays.copyOfRange(arrays,0,index);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

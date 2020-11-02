package com.fuzy.example.leetcode.editor.cn;//给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
//
// 
//
// 说明: 
//
// 
// 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。 
// 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。 
// 
//
// 
//
// 示例: 
//
// 输入:
//nums1 = [1,2,3,0,0,0], m = 3
//nums2 = [2,5,6],       n = 3
//
//输出: [1,2,2,3,5,6] 
// Related Topics 数组 双指针 
// 👍 578 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution12 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int a = 0;
        int b = 0;
        int[] sortArr = new int[n+m];
        int cur;
        while (a<m||b<n){
            if(a==m){
                cur = nums2[b++];
            }else if(b==n){
                cur = nums1[a++];
            }else if(nums1[a]<nums1[b]){
                cur = nums1[a++];
            }else{
                cur = nums2[b++];
            }
            sortArr[a+b-1] = cur;
        }
        System.arraycopy(sortArr,0,nums1,0,n+m);

    }

    public void merge1(int[] nums1, int m, int[] nums2, int n){
        int a = m-1;
        int b = n-1;
        int len = m+n-1;
        while (a>0&&b>0){
            nums1[len] = nums1[a]>nums2[b]?nums1[a--]:nums2[b--];
        }
        System.arraycopy(nums2,0,nums1,0,b+1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

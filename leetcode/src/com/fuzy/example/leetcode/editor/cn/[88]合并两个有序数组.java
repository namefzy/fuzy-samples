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

    public void merge2(int[] nums1, int m, int[] nums2, int n){
        int a = 0;
        int b = 0;
        int[] sortArr = new int[n+m];
        int cur;
        while (a<m||b<n){
            //当a=m时，说明b其他数都大于a了
            if(a==m){
                cur = nums2[b++];
                //当b=n时，说明a其他数都大于b了
            }else if(b==n){
                cur = nums1[a++];
            }else if(nums1[a]<nums1[b]){
                cur = nums1[a++];
            }else{
                cur = nums2[b++];
            }
            sortArr[a+b-1] = cur;
        }
        for (int i = 0; i < n+m; ++i) {
            nums1[i]= sortArr[i];
        }
    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int a=0,b=0,i=0;
        int[] sort = new int[m+n];
        //123 256 有问题，当一个提前结束的时候，依然+1
        while (a<m||b<n){
            if(nums1[a]<nums2[b]){
                sort[i++] = nums1[a++];
            }else if(nums1[a]>nums2[b]){
                sort[i++] = nums1[b++];
            }else if(a==m){
                sort[i++] = nums1[a++];
            }else{
                sort[i++] = nums2[b++];
            }
        }
        System.arraycopy(sort,0,nums1,0,n+m);

    }

    public void merge1(int[] nums1, int m, int[] nums2, int n){
        int a = m-1;
        int b = n-1;
        int len = m+n-1;
        //a移除不完，b先退出 123 456
        while (a>=0&&b>=0){
            nums1[len--] = nums1[a]>nums2[b]?nums1[a--]:nums2[b--];
        }
        /**
         * Object src : 原数组
         *    int srcPos : 从元数据的起始位置开始
         * 　　Object dest : 目标数组
         * 　　int destPos : 目标数组的开始起始位置
         * 　　int length  : 要copy的数组的长度
         */
        System.arraycopy(nums2,0,nums1,0,b+1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

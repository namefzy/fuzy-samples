package com.fuzy.example.leetcode.editor.cn;
//设计一个找到数据流中第K大元素的类（class）。注意是排序后的第K大元素，不是第K个不同的元素。
//
// 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，它包含数据流中的初始元素。每次调用 KthLargest.add，返
//回当前数据流中第K大的元素。 
//
// 示例: 
//
// 
//int k = 3;
//int[] arr = [4,5,8,2];
//KthLargest kthLargest = new KthLargest(3, arr);
// 2 3 4 5 8
//kthLargest.add(3);   // returns 4
// 2 3 4 5 5 8
//kthLargest.add(5);   // returns 5
//2 3 4 5 5 8 10
//kthLargest.add(10);  // returns 5
//2 3 4 5 5 8 9 10
//kthLargest.add(9);   // returns 8
//2 3 4 4 5 5 8 9 19
//kthLargest.add(4);   // returns 8
// 
//
// 说明: 
//你可以假设 nums 的长度≥ k-1 且k ≥ 1。 
// Related Topics 堆 
// 👍 141 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class KthLargest30 {

    public KthLargest30(int k, int[] nums) {

    }
    
    public int add(int val) {
        return 0;
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
//leetcode submit region end(Prohibit modification and deletion)

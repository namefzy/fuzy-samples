package com.fuzy.example.leetcode.editor.cn;//符合下列属性的数组 arr 称为 山峰数组（山脉数组） ：
//
// 
// arr.length >= 3 
// 存在 i（0 < i < arr.length - 1）使得：
// 
// arr[0] < arr[1] < ... arr[i-1] < arr[i] 
// arr[i] > arr[i+1] > ... > arr[arr.length - 1] 
// 
// 
// 
//
// 给定由整数组成的山峰数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 
//1] > ... > arr[arr.length - 1] 的下标 i ，即山峰顶部。 
//
// 
//
// 示例 1： 
//
// 
//输入：arr = [0,1,0]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：arr = [1,3,5,4,2]
//输出：2
// 
//
// 示例 3： 
//
// 
//输入：arr = [0,10,5,2]
//输出：1
// 
//
// 示例 4： 
//
// 
//输入：arr = [3,4,5,1]
//输出：2
// 
//
// 示例 5： 
//
// 
//输入：arr = [24,69,100,99,79,78,67,36,26,19]
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 3 <= arr.length <= 10⁴ 
// 0 <= arr[i] <= 10⁶ 
// 题目数据保证 arr 是一个山脉数组 
// 
//
// 
//
// 进阶：很容易想到时间复杂度 O(n) 的解决方案，你可以设计一个 O(log(n)) 的解决方案吗？ 
//
// 
//
// 注意：本题与主站 852 题相同：https://leetcode-cn.com/problems/peak-index-in-a-mountain-
//array/ 
// Related Topics 数组 二分查找 👍 57 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer69 {
    /**
     * 二分法
     * 1、mid什么时候才合法，当大于左边且大于右边的时候
     * 2、移动左右边界的条件是什么？
     *  当k位置的数既大于left也大于right的时候，
     */
    public int peakIndexInMountainArray1(int[] arr){
        int n = arr.length;
        int left = 1, right = n - 2, ans = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > arr[mid + 1]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
    public int peakIndexInMountainArray(int[] arr) {
        int index = 0;
        for (int i = 1; i < arr.length-1; i++) {
            if(arr[i]>arr[i-1]&&arr[i]>arr[i+1]){
                index = i;
                break;
            }
        }
        return index;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

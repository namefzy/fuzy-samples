package com.fuzy.example.leetcode.editor.cn;//给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
//
// 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。 
//
// 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,0,2,1,1,0]
//输出：[0,0,1,1,2,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,0,1]
//输出：[0,1,2]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：[0]
// 
//
// 示例 4： 
//
// 
//输入：nums = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// n == nums.length 
// 1 <= n <= 300 
// nums[i] 为 0、1 或 2 
// 
//
// 
//
// 进阶： 
//
// 
// 你可以不使用代码库中的排序函数来解决这道题吗？ 
// 你能想出一个仅使用常数空间的一趟扫描算法吗？ 
// 
// Related Topics 排序 数组 双指针 
// 👍 810 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution750 {
    public static void main(String[] args) {
        int[] arr = new int[]{2,0,2,1,1,0};
        sortColors(arr);
        for (int i : arr) {

            System.out.print(i+"\n");
        }
    }
    public static void sortColors(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if(nums[i]>nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j]=temp;
                }
            }
        }
    }
    public void sortColors1(int[] nums){
        int n = nums.length;
        int p0=0,p1=0;
        for (int i = 0; i < n; i++) {
            if(nums[i]==1){
                int temp = nums[i];
                nums[i] = nums[p1];
                nums[p1] = temp;
                ++p1;
            }else if(nums[i]==0){
                int temp = nums[i];
                nums[i] = nums[p0];
                nums[p0] = temp;
                if(p0<p1){
                    temp = nums[i];
                    nums[i] = nums[p1];
                    nums[p1] = temp;
                }
                ++p0;
                ++p1;
            }
        }
    }

    public void sortColors2(int[] nums){
        int len = nums.length;
        if(len<2){
            return;
        }
        int zero = 0;

        int two = len;
        int i = 0;
        while (i<two){
           if(nums[i]==0){
               swap(nums,i,zero);
               zero++;
               i++;
           }else if(nums[1]==1){
               i++;
           }else{
               two--;
               swap(nums,i,two);
           }
        }
    }

    public void sortColors3(int[] nums){
        int len = nums.length;
        int zero = 0;
        int two = len;
        int i = 0;
        while (i<two){
            if(nums[i]==0){
                swap(nums,i,zero);
                zero++;
                i++;
            }else if(nums[1]==1){
                i++;
            }else{
                two--;
                swap(nums,i,two);
            }
        }
    }
    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}
//leetcode submit region end(Prohibit modification and deletion)

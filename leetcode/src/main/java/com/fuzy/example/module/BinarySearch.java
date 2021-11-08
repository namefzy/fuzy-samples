package com.fuzy.example.module;

/**
 * @ClassName BinarySearch
 * @Description 二分查找法分析
 * @Author 11564
 * @Date 2020/11/12 20:20
 * @Version 1.0.0
 */
public class BinarySearch {

    /**
     * 在一个排序数组中找出等于target目标值的索引位置
     * @param nums 排序数组
     * @param target 目标数字
     * @return
     */
    public int method1(int[] nums,int target){
        int left = 0;
        int right = nums.length-1;
        /**例子[1,2] target=2
         * [left,right]
         * 举个例子极端情况下，target=nums[right];
         * 当左节点left=right-1时，mid=left,此时mid=left+right/2 = left,mid[left]<target,left=left+1;
         * 此时left<=right满足条件，mid=right
         */
        while (left<=right){
            //防止整形溢出
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid+1;
            } else if (nums[mid] > target) {
                right = mid-1;
            }
        }
        return -1;
    }
    public int method2(int[] nums,int target){
        int left = 0;
        int right = nums.length;
        /**
         * [left,right)
         * 极端情况下，target=nums[right-1]
         * 当left=right-1时，mid=nums.length-1,此时已经是最后一层查找；如果在执行一层查找当left<=right,则会数组越界
         */
        while (left<right){
            //防止整形溢出
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid+1;
            } else if (nums[mid] > target) {
                right = mid;
            }
        }
        return -1;
    }

    /**
     * [1,2,2,2,3]
     * 左侧边界问题:当mid=target时，继续往左查找，直到查到最左边等于target的元素的索引
     * @param nums
     * @param target
     * @return
     */
    public int left_bound(int[] nums,int target){
        int left = 0;
        int right = nums.length;
        while (left<right){
            int mid = left + (right - left) / 2;
            //相等继续往左边查找
            if(nums[mid]==target){
                right = mid;
            }else if(nums[mid]<target){
                left = mid+1;
            }else if(nums[mid]>target){
                //[left,right)
                right = mid;
            }

        }
        return nums[left]==target?left:-1;
    }

    /**
    *@Description 右边界问题
    *@Param
    *@Return
    *@Author fuzy
    *@Date 2020/11/14
    */
    public int right_bound(int[] nums,int target){
        if(nums.length==0){
            return -1;
        }
        int left=0,right=nums.length;
        while (left<right){
            int mid = left+(right-left)/2;
            if(nums[mid]==target){
                left = mid+1;
            }else if(nums[mid]>target){
                right = mid;
            }else if(nums[mid]<target){
                left = mid+1;
            }
        }
        //由于结束条件是left=right,所以结果是left-1
        //举例：[1,2] 2 当第一次循环num[mid]<target left=0+1;
        return left-1;
    }

}

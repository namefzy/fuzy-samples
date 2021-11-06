package com.fuzy.example.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author fuzy
 * @date 2021/11/6 10:57
 */
public class SortExample {

    /**
     * 冒泡排序
     * 1、先找出最大的元素，放至最后；
     * 2、在找出第二大的元素，放在倒数第二位置；
     * 3、不断的循环
     */
    @Test
    public void bubbleSort(){
        int[] nums = new int[]{3,4,5,1,2,8,6,7};
        //最外层控制循环的次数
        for (int i = 0; i < nums.length; i++) {
            //内层循环控制比较次数，随着循环次数i的增大，内层比较次数也越来越少
            for (int j = 1; j < nums.length-i; j++) {
                //如果前面的数大于后面的数则交换
                if(nums[j-1]>=nums[j]){
                    swap(nums,j-1,j);
                }
            }
        }

        Arrays.stream(nums).forEach(System.out::println);
    }

    /**
     * 选择排序
     * 1、第一个数与后面的数比较，找出最小的数与第一个数交换
     * 2、第二个数与后面的数比较，找出第二小的数与第二个数交换
     * 3、当i=n-1时，结束
     */
    @Test
    public void selectionSort(){
        int[] nums = new int[]{3,4,5,1,2,8,6,7};
        //i控制最外层循环次数，
        for (int i = 0; i < nums.length; i++) {
            //里面的循环代表从当前位置的数一次和后面的数进行比较，找出最小的数交换
            //定义最小位置的索引
            int minIndex = i;
            for (int j = i+1; j < nums.length; j++) {
                //如果后面的数小于当前位置的数，则将最小位置索引定位到当前位置
                if(nums[minIndex]>nums[j]){
                    minIndex = j;
                }
            }
            //将当前位置的数与最小位置的数进行交换
            swap(nums,i,minIndex);
        }
        Arrays.stream(nums).forEach(System.out::println);
    }

    /**
     * 插入排序
     * 1、已第一个元素作为起点，与第二个元素比较，如果大于第二个元素，则交换
     * 2、已第二个元素作为起点，与第三个元素比较，如果大于第三个元素，在与第一个元素比较
     * 3、已第三个元素作为起点，与第四个元素比较，如果大于第四个元素，在比较前两个元素
     */
    @Test
    public void insertSort(){
        int[] nums = new int[]{3,2,5,1,2,8,6,7};
        //外层循环决定在[0,i]区间内进行插入
        for (int i = 0; i < nums.length-1; i++) {
            //取出下一个数，避免在nums[j+1]的时候该值被覆盖
            int nextNum = nums[i+1];
            //内层循环找出在区间[0-i]中插入的位置
            int preIndex = i;
            for (; preIndex >=0; preIndex--) {
                //移动nums[j]到nums[j+1]的位置
                if(nextNum<nums[preIndex]){
                    nums[preIndex+1] = nums[preIndex];
                }else{
                    //如果当前位置的数小于nextNum，则将当前位置后面的一个属赋值nextNum
                    break;
                }
            }
            nums[preIndex+1] = nextNum;
        }
        Arrays.stream(nums).forEach(System.out::println);
    }

    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

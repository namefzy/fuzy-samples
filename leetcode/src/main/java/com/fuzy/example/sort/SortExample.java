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

    @Test
    public void quickSort(){
        int[] nums = new int[]{3,4,8,5,1,7,2};
        fastSort(nums,0,nums.length-1);
        Arrays.stream(nums).forEach(System.out::println);
    }

    public void fastSort(int[] nums,int left,int right){
        if(nums.length<1||left>right){
            return;
        }
        int pivotIndex = partition(nums,left,right);
        if(pivotIndex>left){
            fastSort(nums,left,pivotIndex-1);
        }
        if(pivotIndex<right){
            fastSort(nums,pivotIndex+1,right);
        }
    }

    public int partition(int[] nums,int left,int right){
        int l = left;
        int pivot = nums[left];
        //该循环以left索引位置为基准值，将所有小于pivot的值放到left索引后面，并且记录最后一次小于基准值的索引位置
        for (int i = l+1; i <= right; i++) {
            //如果基准值大于后边的数，则交换l++与i的值
            if(pivot>nums[i]){
                swap(nums,++l,i);
            }
        }
        //将基准值与最后一次小于基准值的交换，确保基准值左边的都小于等于基准值，右边的大于基准值
        swap(nums,left,l);
        return l;
    }

    public void quickSort2(int[] arr,int low,int high){
        int start = low;
        int end = high;
        int key = arr[start];
        while (start != end) {
            //从右向左遍历，当遍历的数小于key时，跳出循环
            while (start<end&&key<=arr[end]){
                end--;
            }
            //从左遍历，当遍历的结果大于key时，跳出循环
            while(start<end&&key>=arr[start]){
                start++;
            }
            //交换start和end，最终的结果确保start位置的数全都小于等key
            if(start!=end){
                swap(arr,start,end);
            }
        }
        //交换start和low，使得key前面的数全都小于key，后面的数全都大于key
        swap(arr,start,low);
        if (start > low) {
            quickSort2(arr, low, start - 1);
        }
        if (end < high) {
            quickSort2(arr, end + 1, high);
        }

    }

    @Test
    public void countSort(){
        //从小到大排序
        int[] nums = new int[]{3,4,8,5,2,1,7,2};


        int[] bucket = new int[8+1];
        Arrays.fill(bucket,0);
        //将nums中的值作为bucket中的key，nums中相同数字的个数作为bucket的value
        for (int num : nums) {
            bucket[num]++;
        }

        //记录遍历到nums数组的索引位置
        int j = 0;
        //遍历数组bucket，一次给数组nums赋值；因为遍历顺序是有序的，即先给nums赋值的肯定为较小的数
        for (int i = 0; i < bucket.length; i++) {
            //当bucket[i]<=0时，即证明i位置没有对应的数值了，此时跳出for循环
            while (j<nums.length&&bucket[i]>0){
                nums[j++]=i;
                bucket[i]--;
            }
        }
        Arrays.stream(nums).forEach(System.out::println);
    }

    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

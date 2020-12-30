package com.fuzy.example.leetcode.editor.cn;
//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2
//] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。 
//
// 示例 1： 
//
// 输入：[3,4,5,1,2]
//输出：1
// 
//
// 示例 2： 
//
// 输入：[2,2,2,0,1]
//输出：0
// 
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-sor
//ted-array-ii/ 
// Related Topics 二分查找 
// 👍 197 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution124 {
    public int minArray(int[] numbers) {
        int left = 0;
        int right = numbers.length-1;
        while (left<right){
            int middle = left+(right-left)/2;
            //中间点大于右节点 在右边
            //中间点小于右节点，肯定在左边
            if(numbers[middle]>numbers[right]){
                left = middle+1;
            }else if(numbers[middle]<numbers[right]){
                right = middle;
            }else{
                //如果相等 则缩小右边范围
                right--;
            }
        }
        return numbers[left];
    }
}
//leetcode submit region end(Prohibit modification and deletion)

package com.fuzy.example.leetcode.editor.cn;//输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
//
// 
//
// 示例 1： 
//
// 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
// 
//
// 示例 2： 
//
// 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
// 
//
// 
//
// 限制： 
//
// 
// 0 <= matrix.length <= 100 
// 0 <= matrix[i].length <= 100 
// 
//
// 注意：本题与主站 54 题相同：https://leetcode-cn.com/problems/spiral-matrix/ 
// Related Topics 数组 矩阵 模拟 
// 👍 281 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer29 {

    public int[] spiralOrder(int[][] matrix) {
        //数组大小
        if(matrix.length == 0) {
            return new int[0];
        }
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1, x = 0;
        int[] res = new int[(right + 1) * (bottom + 1)];

        while(true) {
            //从左到右
            for(int i = left; i <= right; i++) {
                res[x++] = matrix[top][i];
            }


            //从上倒下再次执行循环的条件，必须满足 ++top<=bottom
            if(++top > bottom) {
                break;
            }
            //从上到下
            for(int i = top; i <= bottom; i++) {
                res[x++] = matrix[i][right];
            }


            //从右到左可以再次执行循环的条件，必须满足 left<=--right
            if(left > --right) {
                break;
            }
            for(int i = right; i >= left; i--) {
                res[x++] = matrix[bottom][i];
            }

            //从底到上可以再次执行循环的条件，必须满足 top<=--bottom
            if(top > --bottom) {
                break;
            }
            for(int i = bottom; i >= top; i--) {
                res[x++] = matrix[i][left];
            }

            //从左到右可以再次执行循环的条件，必须满足 ++left<=right
            if(++left > right) {
                break;
            }
        }
        return res;



    }

    /**
     * @param matrix 原始矩阵
     * @param nums 构建的数组
     * @param start 开始遍历行
     * @param end 结束遍历行
     * @param count 记录数组中计数个数
     */
    public void helper(int[][] matrix,int[] nums,int start,int end,int count){
        //递归结束条件
        if(start>end){
            return;
        }
        //如果start==end，说明循环到最后一层里面的最后一个数
        if(start==end){
            nums[++count] = matrix[start][end];
            return;
        }

        //遍历 matrix[j]，赋值给nums
        for (int j = start; j < matrix[start].length; j++) {
            nums[count++] = matrix[start][j];
        }
        //取最后一行竖列
        while (start<=end){
            nums[count++] = matrix[start][end];
            start++;
        }
        //取尾行数据
        for (int j = end; j >=0; j--) {
            nums[count++] = matrix[end][j];
        }
//        //取第一行竖列
//        while ()

    }
}
//leetcode submit region end(Prohibit modification and deletion)

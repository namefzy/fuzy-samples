package com.fuzy.example.leetcode.editor.cn;
//给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
//
// 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数。 
//
// 你可以返回任何满足上述条件的数组作为答案。 
//
// 
//
// 示例： 
//
// 输入：[4,2,5,7]
//输出：[4,5,2,7]
//解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
// 
//
// 
//
// 提示： 
//
// 
// 2 <= A.length <= 20000 
// A.length % 2 == 0 
// 0 <= A[i] <= 1000 
// 
//
// 
// Related Topics 排序 数组 
// 👍 190 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution155 {
     public static void main(String[] args) {
        int[] A =new int[]{4,2,5,7};
         int[] ints = sortArrayByParityII(A);
         for (int anInt : ints) {
             System.out.println(anInt);
         }
     }
    public static int[] sortArrayByParityII(int[] A) {
        for (int i = 0; i < A.length; i++) {
            //偶数位置对应奇数
            if(i%2==0&&A[i]%2!=0){
                for (int j = i+1; j < A.length; j++) {
                    //奇数位置对应偶数
                    if(j%2!=0&&A[j]%2==0){
                        int temp = A[i];
                        A[i] = A[j];
                        A[j] = temp;
                    }
                }
            }else if (i%2!=0&&A[i]%2==0){//奇数位置对应偶数
                for (int j = i+1; j < A.length; j++) {
                    //偶数位置对应奇数
                    if(j%2!=0&&A[j]%2==0){
                        int temp = A[i];
                        A[i] = A[j];
                        A[j] = temp;
                    }
                }
            }
        }
        return A;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

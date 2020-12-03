package com.fuzy.example.leetcode.editor.cn;//输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
//
// 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
//
//
//
// 示例 1：
//
// 输入：target = 9
//输出：[[2,3,4],[4,5]]
//
//
// 示例 2：
//
// 输入：target = 15
//输出：[[1,2,3,4,5],[4,5,6],[7,8]]
//
//
//
//
// 限制：
//
//
// 1 <= target <= 10^5
//
//
//
// 👍 186 👎 0

import java.util.List;
import java.util.ArrayList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution101 {
    public int[][] findContinuousSequence1(int target){
        int i=1;
        int j=1;
        int sum = 0;
        List<int[]> res = new ArrayList<>();
        while (i<=target/2){
            if(sum<target){
                sum+=j;
                j++;
            }else if(sum>target){
                sum-=i;
                i++;
            }else{
                int[] arr = new int[j-i];
                for (int k=i;k<j;k++){
                    arr[k-i] = k;
                }
                res.add(arr);
                sum -=i;
                i++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    public int[][] findContinuousSequence(int target) {
        List<int[]> vec = new ArrayList<>();
        int right = (target-1)/2;
        int sum =0 ;
        for (int i = 1; i <= right; i++) {
            for (int j = i; ; j++) {
                sum+=j;
                if(sum>target){
                    sum = 0;
                    break;
                }else if(sum==target){
                    int[] res = new int[j-i+1];
                    for (int k = i; k <=j; k++) {
                       res[k-i] = k;
                    }
                    vec.add(res);
                    sum = 0;
                    break;
                }
            }
        }
        return vec.toArray(new int[vec.size()][]);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

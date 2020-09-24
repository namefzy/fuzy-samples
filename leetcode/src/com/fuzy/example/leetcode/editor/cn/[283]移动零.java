package com.fuzy.example.leetcode.editor.cn;
//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
//
// 示例: 
//
// 输入: [0,1,0,3,12]
//输出: [1,3,12,0,0] 
//
// 说明: 
//
// 
// 必须在原数组上操作，不能拷贝额外的数组。 
// 尽量减少操作次数。 
// 
// Related Topics 数组 双指针 
// 👍 740 👎 0
//[0,0,1]

//leetcode submit region begin(Prohibit modification and deletion)
class Solution61 {
    public void moveZeroes(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==0){
                for (int j = i; j < nums.length; j++) {
                    if(j==nums.length-1){
                        nums[nums.length-1]=0;
                    }else{
                        nums[j]=nums[j+1];
                    }
                }
            }
        }

        class Solution {
            public void moveZeroes(int[] nums) {
                if(nums==null) {
                    return;
                }
                //两个指针i和j
                int j = 0;
                for(int i=0;i<nums.length;i++) {
                    //当前元素!=0，就把其交换到左边，等于0的交换到右边
                    if(nums[i]!=0) {
                        int tmp = nums[i];
                        nums[i] = nums[j];
                        nums[j++] = tmp;
                    }
                }
            }
        }
        class Solution1 {
            public void moveZeroes(int[] nums) {
                if(nums==null) {
                    return;
                }
                //第一次遍历的时候，j指针记录非0的个数，只要是非0的统统都赋给nums[j]
                int j = 0;
                for(int i=0;i<nums.length;++i) {
                    if(nums[i]!=0) {
                        nums[j++] = nums[i];
                    }
                }
                //非0元素统计完了，剩下的都是0了
                //所以第二次遍历把末尾的元素都赋为0即可
                for(int i=j;i<nums.length;++i) {
                    nums[i] = 0;
                }
            }
        }


    }
}
//leetcode submit region end(Prohibit modification and deletion)

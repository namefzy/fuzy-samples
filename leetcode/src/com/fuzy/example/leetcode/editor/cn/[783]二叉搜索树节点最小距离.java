package com.fuzy.example.leetcode.editor.cn;
//给定一个二叉搜索树的根节点 root，返回树中任意两节点的差的最小值。
//
// 
//
// 示例： 
//
// 输入: root = [4,2,6,1,3,null,null]
//输出: 1
//解释:
//注意，root是树节点对象(TreeNode object)，而不是数组。
//
//给定的树 [4,2,6,1,3,null,null] 可表示为下图:
//
//          4
//        /   \
//      2      6
//     / \    
//    1   3  
//
//最小的差值是 1, 它是节点1和节点2的差值, 也是节点3和节点2的差值。 
//
// 
//
// 注意： 
//
// 
// 二叉树的大小范围在 2 到 100。 
// 二叉树总是有效的，每个节点的值都是整数，且不重复。 
// 本题与 530：https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/ 
//相同 
// 
// Related Topics 树 递归 
// 👍 96 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution83 {
    List<Integer> vals;
    public int minDiffInBST(TreeNode root) {
        vals = new ArrayList();
        dfs(root);
        Collections.sort(vals);
        int min = 0;
        for (int i = 0; i < vals.size()-1; i++) {
            min = Math.min(min,vals.get(i+1)-vals.get(i));
        }
        return min;
    }
    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        vals.add(node.val);
        dfs(node.left);
        dfs(node.right);
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int[] divingBoard(int shorter, int longer, int k) {
        if(k==0){
            return new int[]{};
        }
        if(shorter==longer){
            return new int[]{k*shorter};
        }
        int[] nums = new int[k+1];
        for(int i=0;i<k+1;i++){
            nums[i] = i*longer+(k-i)*shorter;
        }
        return nums;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

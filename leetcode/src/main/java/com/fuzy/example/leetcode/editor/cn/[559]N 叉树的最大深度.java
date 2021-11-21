package com.fuzy.example.leetcode.editor.cn;//给定一个 N 叉树，找到其最大深度。
//
// 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。 
//
// N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：root = [1,null,3,2,4,null,5,6]
//输出：3
// 
//
// 示例 2： 
//
// 
//
// 
//输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,
//null,13,null,null,14]
//输出：5
// 
//
// 
//
// 提示： 
//
// 
// 树的深度不会超过 1000 。 
// 树的节点数目位于 [0, 10⁴] 之间。 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 👍 230 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

import java.util.Arrays;
import java.util.List;

class Solution559 {

    public static void main(String[] args) {
        Node children5 = new Node(5);
        Node children6 = new Node(6);

        Node children1 = new Node(3,Arrays.asList(children5,children6));
        Node children2 = new Node(2);
        Node children3 = new Node(4);
        List<Node> childs = Arrays.asList(children1,children2,children3);
        Node node = new Node(1,childs);

        System.out.println(maxDepth(node));
    }
    private static int ans;
    public static int maxDepth(Node root) {
        return dsf(root);
    }

    private static int dsf(Node root) {
        if(root==null){
            return 0;
        }
        if(root.children==null){
            return 1;
        }
        for (Node child : root.children) {
            ans = Math.max(dsf(child),ans);
        }
        return ans+1;
    }

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

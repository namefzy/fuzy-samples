package com.fuzy.example.leetcode.editor.cn;


import java.util.*;

/**
 * 树的遍历
 *
 * @author fuzy
 * @date 2021/9/11 15:16
 */
class Solution {
    /**
     * 前序遍历 中->左->右
     * 队列写法
     *
     * @return
     */
    public List<Integer> preOrderByQueue(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Deque<TreeNode> queue = new LinkedList<>();
        TreeNode node = root;
        while (!queue.isEmpty() || node != null) {
            while (node != null) {
                list.add(node.val);
                queue.push(node);
                node = node.left;

            }
            node = queue.poll();
            node = node.right;
        }
        return list;
    }

    /**
     * 前序遍历 中->左->右
     * 栈写法
     *
     * @return
     */
    public List<Integer> preOrderByStack(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return list;
    }

    /**
     * 中序遍历 左->中->右边
     * 栈写法
     *
     * @return
     */
    public List<Integer> midOrder(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node.val);
            node = node.right;
        }

        return list;
    }


    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        treeNode.left = left;
        treeNode.right = right;
        afterOrder(treeNode);
    }
    /**
     * 后序遍历 左->右->中
     *
     * 遍历写法
     *
     * @return
     */
    public static List<Integer> afterOrder(TreeNode root){
        Deque<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> ans = new LinkedList<>();
        if (null == root) {
            return ans;
        }
        stack.addFirst(root);
        while(!stack.isEmpty()) {
            TreeNode node = stack.removeFirst();
            ans.addFirst(node.val);
            if (null != node.left) {
                stack.addFirst(node.left);
            }
            if (null != node.right) {
                stack.addFirst(node.right);
            }
        }
        return ans;
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

package com.fuzy.example.leetcode.editor.cn;//给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
//
// 示例 1: 
//
// 输入: 1->1->2
//输出: 1->2
// 
//
// 示例 2: 
//
// 输入: 1->1->2->3->3
//输出: 1->2->3 
// Related Topics 链表 
// 👍 365 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution9 {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode concurrent = head;
        while (concurrent!=null&&concurrent.next!=null){
            if (concurrent.val == concurrent.next.val) {
                concurrent.next = concurrent.next.next;
            }else{
                concurrent = concurrent.next;
            }
        }
        return head;
    }
    public class ListNode{
        int val;
        ListNode next;
        ListNode(int x){val=x;}
    }
}
//leetcode submit region end(Prohibit modification and deletion)

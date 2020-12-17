package com.fuzy.example.leetcode.editor.cn.leetcode.editor.cn;
//删除链表中等于给定值 val 的所有节点。
//
// 示例: 
//
// 输入: 1->2->6->3->4->5->6, val = 6
//输出: 1->2->3->4->5
// 
// Related Topics 链表 
// 👍 501 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution115 {
    public ListNode removeElements(ListNode head, int val) {
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode temp = pre,cur = head;
        while (cur!=null){
            if(cur.val==val){
                temp.next = cur.next;
            }else{
                temp = cur;
            }
            cur = cur.next;
        }
        return pre.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

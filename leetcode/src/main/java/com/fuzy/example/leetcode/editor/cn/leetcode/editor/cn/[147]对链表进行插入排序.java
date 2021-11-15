package com.fuzy.example.leetcode.editor.cn.leetcode.editor.cn;//对链表进行插入排序。
//
// 
//插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。 
//每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。 
//
// 
//
// 插入排序算法： 
//
// 
// 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。 
// 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。 
// 重复直到所有输入数据插入完为止。 
// 
//
// 
//
// 示例 1： 
//
// 输入: 4->2->1->3
//输出: 1->2->3->4
// 
//
// 示例 2： 
//
// 输入: -1->5->3->4->0
//输出: -1->0->3->4->5
// 
// Related Topics 链表 排序 👍 445 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution147 {
    public ListNode insertionSortList(ListNode head) {

        ListNode sentinel = new ListNode();
        sentinel.next = head;

        ListNode current = head.next;
        ListNode lastSorted = head;
        while (current!=null){
            if(current.val>=lastSorted.val){
                lastSorted = lastSorted.next;
            }else{
                ListNode pre = sentinel;
                //从头开始遍历链表，结束位置
                while (current.val>=pre.val){
                    pre = pre.next;
                }
                lastSorted.next = current.next;
                current.next = pre.next;
                pre.next = current;

            }
            current = lastSorted.next;
        }

        return sentinel.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

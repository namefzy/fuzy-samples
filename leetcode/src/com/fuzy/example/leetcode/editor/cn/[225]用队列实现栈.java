package com.fuzy.example.leetcode.editor.cn;
//使用队列实现栈的下列操作：
//
// 
// push(x) -- 元素 x 入栈 
// pop() -- 移除栈顶元素 
// top() -- 获取栈顶元素 
// empty() -- 返回栈是否为空 
// 
//
// 注意: 
//
// 
// 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合
//法的。 
// 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。 
// 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。 
// 
// Related Topics 栈 设计 
// 👍 219 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class MyStack42 {

    private List<Integer> list;
    /** Initialize your data structure here. */
    public MyStack42() {
        list = new ArrayList<>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        list.add(x);
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        Integer remove = list.remove(0);
        return remove;
    }
    
    /** Get the top element. */
    public int top() {
        Integer integer = list.get(0);
        return integer;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return list.isEmpty();
    }

    public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(lists.get(i));
        }
        lists.remove(0);
        System.out.println(lists);
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
//leetcode submit region end(Prohibit modification and deletion)

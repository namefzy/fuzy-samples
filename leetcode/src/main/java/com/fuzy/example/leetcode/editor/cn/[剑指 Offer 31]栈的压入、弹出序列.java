package com.fuzy.example.leetcode.editor.cn;//输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如，序列 {1,2,3,4,5} 是某栈
//的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列。 
//
// 
//
// 示例 1： 
//
// 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
//输出：true
//解释：我们可以按以下顺序执行：
//push(1), push(2), push(3), push(4), pop() -> 4,
//push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
// 
//
// 示例 2： 
//
// 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
//输出：false
//解释：1 不能在 2 之前弹出。
// 
//
// 
//
// 提示： 
//
// 
// 0 <= pushed.length == popped.length <= 1000 
// 0 <= pushed[i], popped[i] < 1000 
// pushed 是 popped 的排列。 
// 
//
// 注意：本题与主站 946 题相同：https://leetcode-cn.com/problems/validate-stack-sequences/ 
// Related Topics 栈 数组 模拟 👍 234 👎 0


import java.util.Objects;
import java.util.Stack;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionOffer31 {

    /**
     * 1、B中4不匹配5，添加到辅助栈
     * 2、B中3不匹配5，添加到辅助栈
     * 3、5匹配5，不添加到辅助栈，同时将A中的5弹出
     * 4、因为C中要弹出的是3，所以从B中寻找4，最终B为空
     * 5、此时辅助栈可能不为空，A也不为空，同时弹出，
     *
     * @param pushed {1,2,3,4,5}
     * @param popped {4,5,3,2,1}
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        for(int num : pushed) {
            stack.push(num); // num 入栈
            while(!stack.isEmpty() && stack.peek() == popped[i]) { // 循环判断与出栈
                stack.pop();
                i++;
            }
        }
        return stack.isEmpty();


    }
}
//leetcode submit region end(Prohibit modification and deletion)

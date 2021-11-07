package com.fuzy.example.leetcode.editor.cn;
//通常，正整数 n 的阶乘是所有小于或等于 n 的正整数的乘积。例如，factorial(10) = 10 * 9 * 8 * 7 * 6 * 5 * 4 *
// 3 * 2 * 1。 
//
// 相反，我们设计了一个笨阶乘 clumsy：在整数的递减序列中，我们以一个固定顺序的操作符序列来依次替换原有的乘法操作符：乘法(*)，除法(/)，加法(+)
//和减法(-)。 
//
// 例如，clumsy(10) = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1。然而，这些运算仍然使用通常的算术运算顺序：我
//们在任何加、减步骤之前执行所有的乘法和除法步骤，并且按从左到右处理乘法和除法步骤。 
//
// 另外，我们使用的除法是地板除法（floor division），所以 10 * 9 / 8 等于 11。这保证结果是一个整数。 
//
// 实现上面定义的笨函数：给定一个整数 N，它返回 N 的笨阶乘。 
//
// 
//
// 示例 1： 
//
// 输入：4
//输出：7
//解释：7 = 4 * 3 / 2 + 1
// 
//
// 示例 2： 
//
// 输入：10
//输出：12     n-i/4=1 乘 n-i/4=2 除 n-i/4=3 加 n-i/4=0 减
//解释：12 = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= N <= 10000 
// -2^31 <= answer <= 2^31 - 1 （答案保证符合 32 位整数。） 
// 
// Related Topics 数学 
// 👍 87 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution1008 {

    public static void main(String[] args) {
        System.out.printf(String.valueOf(clumsy(4)));
    }


    public static int clumsy(int N) {
        if(N<=1){
            return N;
        }
        int sum = 0;
        //解释：12 = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1
        //以10为例，每减少3个数，就相加，所以先把+号的求和
        for (int i = 0; i < N; i++) {
            if((N-i)%4==3){
                sum+=i;
            }
        }
        //第一次加入sum,其他的都减去
        boolean flag = true;

        for (int i = N; i >= 1; i--) {
            //外层循环计算差值
            int value = i;
            //内层循环计算积和商
            for (int j = 1; j <4&i>1; j++) {
                if(j==1){
                    value *=(value-j);
                }else if(j==2){
                    value= value/(i-1);
                }
                i--;
            }
            if(flag){
                sum +=value;
            }else{
                sum -= value;
            }
            flag = false;
        }
        return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

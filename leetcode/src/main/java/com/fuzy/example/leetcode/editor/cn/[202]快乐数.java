package com.fuzy.example.leetcode.editor.cn;
//编写一个算法来判断一个数 n 是不是快乐数。
//
// 「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
//如果 可以变为 1，那么这个数就是快乐数。 
//
// 如果 n 是快乐数就返回 True ；不是，则返回 False 。 
//
// 
//
// 示例： 
//
// 输入：19
//输出：true
//解释：
//12 + 92 = 82
//82 + 22 = 68
//62 + 82 = 100
//12 + 02 + 02 = 1
// 
// Related Topics 哈希表 数学 
// 👍 440 👎 0


import java.util.HashSet;
import java.util.Set;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution58 {
    public static void main(String[] args) {
        boolean happy = isHappy1(19);
        System.out.println(happy);
    }

    public boolean isHappy(int n){
        Set<Integer> set = new HashSet<>();
        while (n!=1&&!set.contains(n)){
            set.add(n);
            n = getNext(n);
        }
        return n==1;
    }

    private int getNext(int n) {
        int totalSum = 0;
        while (n>0){
            int d = n%10;
            n = n/10;
            totalSum += d*d;
        }
        return totalSum;
    }

    public static boolean isHappy1(int n) {

        while (true){
            String s = String.valueOf(n);
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if(i==0){
                    n = 0;
                }
                n += (int) Math.pow((int)chars[i]-(int)('0'),2);
            }
            if(n==1){
                return true;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

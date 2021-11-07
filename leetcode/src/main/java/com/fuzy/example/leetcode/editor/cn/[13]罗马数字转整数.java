package com.fuzy.example.leetcode.editor.cn;//罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
//
// 字符          数值
//I             1
//V             5
//X             10
//L             50
//C             100
//D             500
//M             1000 
//
// 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做 XXVII, 即为 XX + V + I
//I 。 
//
// 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5
// 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况： 
//
// 
// I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。 
// X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
// C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。 
// 
//
// 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。 
//
// 示例 1: 
//
// 输入: "III"
//输出: 3 
//
// 示例 2: 
//
// 输入: "IV"
//输出: 4 
//
// 示例 3: 
//
// 输入: "IX"
//输出: 9 
//
// 示例 4: 
//
// 输入: "LVIII"
//输出: 58
//解释: L = 50, V= 5, III = 3.
// 
//
// 示例 5: 
//
// 输入: "MCMXCIV"
//输出: 1994
//解释: M = 1000, CM = 900, XC = 90, IV = 4. 
// Related Topics 数学 字符串 
// 👍 984 👎 0


import java.util.HashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution02 {
    private static Map<String,Integer> map = new HashMap<>();
    private static Map<String,Integer> composeMap = new HashMap<>();
    static {
        map.put("I",1);
        map.put("V",5);
        map.put("X",10);
        map.put("L",50);
        map.put("C",100);
        map.put("D",500);
        map.put("M",1000);
        composeMap.put("IV",4);
        composeMap.put("IX",9);
        composeMap.put("XL",40);
        composeMap.put("XC",90);
        composeMap.put("CD",400);
        composeMap.put("CM",900);
    }
    public static void main(String[] args) {
        System.out.println(romanToInt1("MCMXCIV"));
    }

    public static int removeDuplicates(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        int p = 0;
        int q = 0;
        while (q<nums.length){
            if(nums[p]!=nums[q]){
                nums[p+1] = nums[q];
                p++;
            }
            q++;
        }

        return p+1;
    }
    /**
     * 转换罗马字符串
     * 官方解法
     */
    public static int romanToInt2(String s){
        Map<String,Integer> cusMap = new HashMap<>();
        cusMap.put("I",1);
        cusMap.put("V",5);
        cusMap.put("X",10);
        cusMap.put("L",50);
        cusMap.put("C",100);
        cusMap.put("D",500);
        cusMap.put("M",1000);
        cusMap.put("IV",4);
        cusMap.put("IX",9);
        cusMap.put("XL",40);
        cusMap.put("XC",90);
        cusMap.put("CD",400);
        cusMap.put("CM",900);

        int result = 0;
        for (int i = 0; i < s.length();) {
            if(i+1<s.length()&&cusMap.containsKey(s.substring(i,i+2))){
                result+=cusMap.get(s.substring(i,i+2));
                i+=2;
            }else{
                result+=cusMap.get(s.substring(i,i+1));
                i+=1;
            }
        }
        return result;
    }
    /**
     * 转换罗马字符串
     * 耗时耗空间解法
     * @param s
     * @return
     */
    public static int romanToInt1(String s){
        Integer result = 0;

        for (Map.Entry<String, Integer> entry : composeMap.entrySet()) {
            if(s.contains(entry.getKey())){
                result +=entry.getValue();
                s = s.replaceAll(entry.getKey(),"");
            }
        }
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if(map.containsKey(String.valueOf(aChar))){
                result +=map.get(String.valueOf(aChar));
            }
        }

        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

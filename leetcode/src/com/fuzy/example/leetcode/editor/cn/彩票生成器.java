package com.fuzy.example.leetcode.editor.cn;


import java.util.*;

/**
 * @author fuzy
 * @version 1.0
 * @Description
 * @company 上海有分科技发展有限公司
 * @email fuzy@ufen.cn
 * @date 2020/12/17 10:22
 */
public class 彩票生成器 {

    private static List<Integer> redList = new ArrayList<>();

    private static List<Integer> blueList = new ArrayList<>();

    static {
        for (int i = 1; i < 34; i++) {
            redList.add(i);
        }
        for (int i = 1; i < 17; i++) {
            blueList.add(i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println("您本次投注的号码："+fuli());
        }
    }

    /**
     * 福利彩票：红色球号码从1--33中选择；蓝色球号码从1--16中选择。
     * @return
     */
    public static String fuli(){
        StringBuilder sb = new StringBuilder();
        Set<Integer> result = new HashSet<>();
        Random redRandom = new Random();
        while (true){
            int randomNum = redRandom.nextInt(33)+1;
            result.add(randomNum);
            if(result.size()>=6){
                break;
            }
        }
        Object[] objects = result.toArray();
        Arrays.sort(objects);
        for (Object integer : objects) {
            if((Integer)integer<10){
                sb.append("0").append(integer).append(" ");
            }else{
                sb.append(integer).append(" ");
            }
        }
        Random blueRandom = new Random();
        int i = blueRandom.nextInt(16)+1;
        if(i<10){
            sb.append("+ ").append("0").append(i);
        }else{
            sb.append("+ ").append(i);
        }

        return sb.toString();
    }
}

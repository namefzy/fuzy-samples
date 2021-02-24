package com.fuzy.example.leetcode.editor.cn;
//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
//
// 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
// 
//
// 示例 2： 
//
// 
//输入：s = "a", t = "a"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length, t.length <= 105 
// s 和 t 由英文字母组成 
// 
//
// 
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 834 👎 0


import com.sun.xml.internal.ws.policy.subject.WsdlBindingSubject;

import java.util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution93 {

    public String minWindow2(String s,String t){
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //维护两个数组，记录已有字符串指定字符的出现次数，和目标字符串指定字符的出现次数
        //ASCII表总长128
        int[] need = new int[128];
        int[] have = new int[128];

        //将目标字符串指定字符的出现次数记录
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
        int left = 0,right =0,min=s.length()+1,count = 0,start=0;
        while (right<s.length()){
            char r = s.charAt(right);
            if(need[r]==0){
                right++;
                continue;
            }
            if(have[r]<need[r]){
                count++;
            }
            have[r]++;
            right++;
            while (count==t.length()){
                if(right-left<min){
                    min = right - left;
                    start = left;
                }
                char l = s.charAt(left);
                if(need[l]==0){
                    left++;
                    continue;
                }
                if(have[l]==need[l]){
                    count--;
                }
                //已有字符串中目标字符出现的次数-1
                have[l]--;
                //移动左指针
                left++;
            }
        }
        //如果最小长度还为初始值，说明没有符合条件的子串
        if (min == s.length() + 1) {
            return "";
        }
        //返回的为以记录的起始位置为起点，记录的最短长度为距离的指定字符串中截取的子串
        return s.substring(start, start + min);
    }

    public String minWindow1(String s, String t) {
        Map<Character,Integer> need = new HashMap<>();
        Map<Character,Integer> windows = new HashMap<>();
        for (char c : t.toCharArray()) {
            if(need.containsKey(c)){
                need.put(c,need.get(c)+1);
            }else {
                need.getOrDefault(c,1);
            }
            windows.put(c,0);
        }

        int left = 0,right=0;
        int valid = 0;
        int start = 0,len=Integer.MAX_VALUE;
        while(right<s.length()){
            //c是将移入窗口的字符
            char c =s.charAt(right);
            //右移窗口
            right++;
            //进行窗口数据更新
            if(need.containsKey(c)){
                windows.put(c,windows.get(c)+1);
                //某个字符已经满足全部获取到了
                if(windows.get(c).equals(need.get(c))){
                    valid++;
                }
            }
            //判断左侧窗口是否要收缩
            while(valid==need.size()){
                //在这里更新最小覆盖字串
                if(right-left<len){
                    start = left;
                    len = right-left;
                }
                //d是将移除窗口的字符
                char d = s.charAt(left);
                //左移窗口
                left++;
                //进行窗口内数据的一系列更新
                if(need.containsKey(d)){
                    if(windows.get(d).equals(need.get(d))){
                        valid--;
                    }
                    windows.put(d,windows.get(d)-1);
                }
            }
        }
           return len == Integer.MAX_VALUE?"":s.substring(start,len);

    }
    public String minWindow(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //维护两个数组，记录已有字符串指定字符的出现次数，和目标字符串指定字符的出现次数
        //ASCII表总长128
        int[] need = new int[128];
        int[] have = new int[128];

        //将目标字符串指定字符的出现次数记录
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        //分别为左指针，右指针，最小长度(初始值为一定不可达到的长度)
        //已有字符串中目标字符串指定字符的出现总频次以及最小覆盖子串在原字符串中的起始位置
        int left = 0, right = 0, min = s.length() + 1, count = 0, start = 0;

        //s = "ADOBECODEBANC", t = "ABC"
        while (right < s.length()) {
            char r = s.charAt(right);
            //说明该字符不被目标字符串需要，此时有两种情况
            // 1.循环刚开始，那么直接移动右指针即可，不需要做多余判断
            // 2.循环已经开始一段时间，此处又有两种情况
            //  2.1 上一次条件不满足，已有字符串指定字符出现次数不满足目标字符串指定字符出现次数，那么此时
            //      如果该字符还不被目标字符串需要，就不需要进行多余判断，右指针移动即可
            //  2.2 左指针已经移动完毕，那么此时就相当于循环刚开始，同理直接移动右指针
            if (need[r] == 0) {
                right++;
                continue;
            }
            //当且仅当已有字符串目标字符出现的次数小于目标字符串字符的出现次数时，count才会+1
            //是为了后续能直接判断已有字符串是否已经包含了目标字符串的所有字符，不需要挨个比对字符出现的次数
            if (have[r] < need[r]) {
                count++;
            }
            //已有字符串中目标字符出现的次数+1
            have[r]++;
            //移动右指针
            right++;
            //当且仅当已有字符串已经包含了所有目标字符串的字符，且出现频次一定大于或等于指定频次
            while (count == t.length()) {
                //挡窗口的长度比已有的最短值小时，更改最小值，并记录起始位置
                if (right - left < min) {
                    min = right - left;
                    start = left;
                }
                char l = s.charAt(left);
                //如果左边即将要去掉的字符不被目标字符串需要，那么不需要多余判断，直接可以移动左指针
                if (need[l] == 0) {
                    left++;
                    continue;
                }
                //如果左边即将要去掉的字符被目标字符串需要，且出现的频次正好等于指定频次，那么如果去掉了这个字符，
                //就不满足覆盖子串的条件，此时要破坏循环条件跳出循环，即控制目标字符串指定字符的出现总频次(count）-1
                if (have[l] == need[l]) {
                    count--;
                }
                //已有字符串中目标字符出现的次数-1
                have[l]--;
                //移动左指针
                left++;
            }
        }
        //如果最小长度还为初始值，说明没有符合条件的子串
        if (min == s.length() + 1) {
            return "";
        }
        //返回的为以记录的起始位置为起点，记录的最短长度为距离的指定字符串中截取的子串
        return s.substring(start, start + min);
    }
}
//leetcode submit region end(Prohibit modification and deletion)

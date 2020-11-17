package com.fuzy.example.leetcode.editor.cn;
//二进制手表顶部有 4 个 LED 代表 小时（0-11），底部的 6 个 LED 代表 分钟（0-59）。
//
// 每个 LED 代表一个 0 或 1，最低位在右侧。 
//
// 
//
// 例如，上面的二进制手表读取 “3:25”。 
//
// 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。 
//
// 
//
// 示例： 
//
// 输入: n = 1
//返回: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "
//0:32"] 
//
// 
//
// 提示： 
//
// 
// 输出的顺序没有要求。 
// 小时不会以零开头，比如 “01:00” 是不允许的，应为 “1:00”。 
// 分钟必须由两位数组成，可能会以零开头，比如 “10:2” 是无效的，应为 “10:02”。 
// 超过表示范围（小时 0-11，分钟 0-59）的数据将会被舍弃，也就是说不会出现 "13:00", "0:61" 等时间。 
// 
// Related Topics 位运算 回溯算法 
// 👍 188 👎 0
import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution91 {
    List<String> list = new ArrayList<>();
    int[] a=new int[]{1,2,4,8,1,2,4,8,16,32};
    public List<String> readBinaryWatch(int num) {
        List<String> ans = new ArrayList<>();
        if(num>=9){
            return ans;
        }
        for (int i = 0; i < 3; i++) {
            if(num-i>5){
                continue;
            }
            List<String> h = new ArrayList<>();
            //
            helperHour(i,0,0,new int[4],h);
            List<String> m = new ArrayList<>();
            helperHour(num-i,0,0,new int[6],m);

        }
        return ans;
    }

    private void helperHour(int num, int idx, int cnt, int[] arr, List<String> res) {
        if(cnt==num){
            StringBuilder sb = new StringBuilder();
            for(int val : arr) {
                sb.append(val);
            }
            int tmp = Integer.parseInt(sb.toString(), 2);
            if((tmp > 11 && arr.length == 4) || (tmp > 59 && arr.length == 6)){
                return;
            }
            res.add(Integer.toString(tmp));
            return;
        }
        for(int i = idx; i < arr.length; i++) {
            if(arr[i] != 0){
                continue;
            }
            arr[i] = 1;
            helperHour(num, i + 1, cnt + 1, arr, res);
            arr[i] = 0;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)

package com.fuzy.example.leetcode.editor.cn;//「力扣挑战赛」心算项目的挑战比赛中，要求选手从 `N` 张卡牌中选出 `cnt` 张卡牌，若这 `cnt` 张卡牌数字总和为偶数，则选手成绩「有效」且得分为
// `cnt` 张卡牌数字总和。
//给定数组 `cards` 和 `cnt`，其中 `cards[i]` 表示第 `i` 张卡牌上的数字。 请帮参赛选手计算最大的有效得分。若不存在获取有效得分
//的卡牌方案，则返回 0。
//
//**示例 1：**
//>输入：`cards = [1,2,8,9], cnt = 3`
//>
//>输出：`18`
//>
//>解释：选择数字为 1、8、9 的这三张卡牌，此时可获得最大的有效得分 1+8+9=18。
//
//**示例 2：**
//>输入：`cards = [3,3,1], cnt = 1`
//>
//>输出：`0`
//>
//>解释：不存在获取有效得分的卡牌方案。
//
//**提示：**
//- `1 <= cnt <= cards.length <= 10^5`
//- `1 <= cards[i] <= 1000`
//
//
// 👍 15 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class SolutionLCP40 {

    /**
     * [1,2,8,9,10]
     * 1,2,8不符合
     * 1,2,9 符合
     *
     * @param cards
     * @param cnt
     * @return
     */
    public  int maxmiumScore(int[] cards, int cnt) {
        //[9,8,2,1]
        Arrays.sort(cards);
        //存储所有奇数的前缀和
        List<Integer> odd = new ArrayList<>();
        //存储所有偶数的前缀和
        List<Integer> even = new ArrayList<>();
        // 0个奇数/偶数时，值为0
        odd.add(0);
        even.add(0);
        // 从大到小遍历，同时求出前缀和
        for(int i = cards.length-1; i >= 0; i--) {
            //0,1,9
            if (cards[i] % 2 == 1) {
                odd.add(odd.get(odd.size() - 1) + cards[i]);
            } else {
                //0,2,8
                even.add(even.get(even.size()-1) + cards[i]);
            }
        }
        int k = 0;
        int ans = 0;
        // k个奇数，cnt个偶数，同时满足三个条件
        // 1: k > 奇数的个数
        // 2: cnt-k > 偶数的个数
        // 3: 总数和为偶数
        while (k <= cnt) {
            // 选k个奇数， cnt - k 个偶数
            if (k < odd.size() && cnt - k < even.size() && (odd.get(k)+even.get(cnt-k)) % 2 == 0){
                ans = Math.max(ans, odd.get(k) + even.get(cnt-k));
            }
            k++;
        }
        return ans;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

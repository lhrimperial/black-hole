package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic123 {

    /**
     * 123. 买卖股票的最佳时机 III
     *
     * <p>给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     *
     * <p>设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     */
    public static void main(String[] args) {
        int[] prices = {3, 3, 5, 0, 0, 3, 1, 4};
        System.out.println(maxProfit(prices));
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int len = prices.length;
        int b1 = -prices[0], s1 = 0;
        int b2 = -prices[0], s2 = 0;
        for (int i = 1; i < len; i++) {
            b1 = Math.max(b1, -prices[i]);
            s1 = Math.max(s1, b1 + prices[i]);
            b2 = Math.max(b2, s1 - prices[i]);
            s2 = Math.max(s2, b2 + prices[i]);
        }
        return s2;
    }
}

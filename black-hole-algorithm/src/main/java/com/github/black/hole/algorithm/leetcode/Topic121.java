package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic121 {

    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *
     * <p>你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *
     * <p>返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     */
    public static void main(String[] args) {
        int[] arr = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(arr));
        System.out.println(maxProfit1(arr));
    }

    public static int maxProfit1(int[] prices){
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int dp_i_0 = 0, dp_i_1 = -prices[0];
        for (int i = 0, len = prices.length; i < len; i++) {
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        // 天数 持股情况 0未持股 1持有股票
        int[][] dp = new int[n][2];
        // 第一天未买入
        dp[0][0] = 0;
        // 第一天买入
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            // 第i天未持股情况 有i-1天转移过了 未操作 前一天持股卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }

    public static int maxProfit11(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 0, len = prices.length; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                res = Math.max(res, prices[j] - prices[i]);
            }
        }
        return res;
    }
}

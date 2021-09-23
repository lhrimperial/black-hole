package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic188 {

    /**
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/solution/javayi-ge-si-lu-da-bao-suo-you-gu-piao-t-pd1p/
     * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
     *
     * <p>设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
     *
     * <p>注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     */
    public static void main(String[] args) {
        int k = 2;
        int[] prices = {2, 4, 1};
        System.out.println(maxProfit(k, prices));
    }

    public static int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        k = Math.min(k, n / 2);
        int[][][] dp = new int[n][k + 1][2];
        for (int i = 0; i < k; i++) {
            dp[0][k][0] = 0;
            dp[0][k][1] = -prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }
        return dp[n - 1][k][0];
    }
}

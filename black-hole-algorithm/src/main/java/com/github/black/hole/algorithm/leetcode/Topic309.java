package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic309 {

    /**
     * 309. 最佳买卖股票时机含冷冻期 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
     *
     * <p>设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     *
     * <p>你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 0, 2};
        System.out.println(maxProfit(arr));
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
        dp[1][1] = Math.max(dp[0][1], dp[0][0] - prices[1]);
        for (int i = 2; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }
}

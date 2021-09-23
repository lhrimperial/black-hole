package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Bknapsack {

    /**
     * 「力扣」上的 0-1 背包问题：
     *
     * <p>「力扣」第 416 题：分割等和子集（中等）；
     *
     * <p>「力扣」第 474 题：一和零（中等）；
     *
     * <p>「力扣」第 494 题：目标和（中等）；
     *
     * <p>「力扣」第 879 题：盈利计划（困难）；
     *
     * <p>「力扣」上的 完全背包问题：
     *
     * <p>「力扣」第 322 题：零钱兑换（中等）；
     *
     * <p>「力扣」第 518 题：零钱兑换 II（中等）；
     *
     * <p>「力扣」第 1449 题：数位成本和为目标值的最大数字（困难）。
     *
     * <p>这里要注意鉴别：「力扣」第 377 题，不是「完全背包」问题。
     */
    public static void main(String[] args) {
        int res = knapsack(4, 3, new int[] {2, 1, 3}, new int[] {4, 2, 3});
        System.out.println(res);
    }

    public static int knapsack(int W, int N, int[] wt, int[] val) {
        int[][] dp = new int[N + 1][W + 1];
        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {
                if (w - wt[n - 1] < 0) {
                    dp[n][w] = dp[n - 1][w];
                } else {
                    dp[n][w] = Math.max(dp[n - 1][w], dp[n - 1][w - wt[n - 1]] + val[n - 1]);
                }
            }
        }
        return dp[N][W];
    }
}

package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic518 {

    /**
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     *
     * <p>请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     *
     * <p>假设每一种面额的硬币有无限个。
     *
     * <p>题目数据保证结果符合 32 位带符号整数。
     */
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        System.out.println(change(5, coins));
    }

    public static int change(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount == 0) {
            return 0;
        }
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
}

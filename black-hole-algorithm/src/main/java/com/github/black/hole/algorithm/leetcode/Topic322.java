package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/21
 */
public class Topic322 {
    /**
     * 322.零钱兑换（中等）
     *
     * <p>组成给定金额需要最少硬币个数
     */
    public static void main(String[] args) {
        System.out.println(coinChange1(new int[] {1, 2, 5}, 11));
        System.out.println(coinChange(new int[] {1, 2, 5}, 11));
    }

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        // base case
        dp[0] = 0;
        for (int i = 0, len = dp.length; i < len; i++) {
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return dp[amount];
    }

    /**
     * 状态转移方程，暴力递归
     *
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange1(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, -1);
        return dp1(coins, amount, memo);
    }

    public static int dp1(int[] coins, int amount, int[] memo) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        if (memo[amount] != -1) {
            System.out.println("amount=" + amount + ",memo=" + Arrays.toString(memo));
            return memo[amount];
        }

        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 计算子问题
            int sub = dp1(coins, amount - coin, memo);
            if (sub < 0) {
                continue;
            }
            res = Math.min(res, sub + 1);
        }
        memo[amount] = res;
        return res;
    }
}

package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/3
 */
public class Topic279 {
    /** 279. 完全平方数 */
    public static void main(String[] args) {
        System.out.println(numSquares(13));
    }

    public static int numSquares(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                min = Math.min(min, dp[i - j * j]);
            }
            dp[i] = min + 1;
        }
        return dp[n];
    }
}

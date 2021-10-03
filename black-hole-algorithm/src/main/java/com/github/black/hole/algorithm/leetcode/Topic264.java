package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic264 {
    /** 264. 丑数 II */
    public static void main(String[] args) {
        System.out.println(nthUglyNumber(11));
    }

    public static int nthUglyNumber(int n) {
        if (n <= 0) {
            return -1;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        int p2 = 1, p3 = 1, p5 = 1;
        for (int i = 2; i <= n; i++) {
            int num2 = dp[p2] * 2, num3 = dp[p3] * 3, num5 = dp[p5] * 5;
            dp[i] = Math.min(num2, Math.min(num3, num5));
            if (dp[i] == num2) {
                p2++;
            }
            if (dp[i] == num3) {
                p3++;
            }
            if (dp[i] == num5) {
                p5++;
            }
        }
        return dp[n];
    }
}

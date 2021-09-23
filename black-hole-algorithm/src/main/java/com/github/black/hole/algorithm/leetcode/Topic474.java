package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic474 {
    /**
     * 一和零 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
     *
     * <p>请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
     *
     * <p>如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
     *
     * <p>
     */
    public static void main(String[] args) {
        String[] strs = {"10", "0001", "111001", "1", "0"};
        System.out.println(findMaxForm(strs, 5, 3));
    }

    public static int findMaxForm(String[] strs, int m, int n) {
        if (strs == null || strs.length == 0) {
            return 0;
        }
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1];
        for (int i = 1; i <= len; i++) {
            int[] count = countBit(strs[i - 1]);
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    int zero = count[0];
                    int one = count[1];
                    if (j >= zero && k >= one) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zero][k - one] + 1);
                    }
                }
            }
        }
        return dp[len][m][n];
    }

    private static int[] countBit(String value) {
        int[] res = new int[2];
        for (char ch : value.toCharArray()) {
            res[ch - '0']++;
        }
        return res;
    }
}

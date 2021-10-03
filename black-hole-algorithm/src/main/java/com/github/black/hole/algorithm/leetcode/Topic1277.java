package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic1277 {
    /** 1277. 统计全为 1 的正方形子矩阵 */
    public static void main(String[] args) {
        int[][] matrix = {
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {0, 1, 1, 1}
        };
        System.out.println(countSquares(matrix));
    }

    public static int countSquares(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int n = matrix.length, m = matrix[0].length;
        int[][] dp = new int[n][m];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                        ans++;
                    } else {
                        dp[i][j] =
                                Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1]))
                                        + 1;
                        ans += dp[i][j];
                    }
                }
            }
        }
        return ans;
    }
}

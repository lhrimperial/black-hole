package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic97 {
    /** 97. 交错字符串 */
    public static void main(String[] args) {
        String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac";
        System.out.println(isInterleave(s1, s2, s3));
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        int n = s1.length(), m = s2.length(), k = s3.length();
        if (n + m != k) {
            return false;
        }
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                int p = i + j - 1;
                if (i > 0) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    dp[i][j] = dp[i][j] || dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p);
                }
            }
        }
        return dp[n][m];
    }
}

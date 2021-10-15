package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic72 {
    /**
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
     *
     * <p>你可以对一个单词进行如下三种操作：
     *
     * <p>插入一个字符 删除一个字符 替换一个字符
     */
    public static void main(String[] args) {
        String word1 = "horse", word2 = "ros";
        System.out.println(minDistance(word1, word2));
    }

    public static int minDistance(String word1, String word2) {
        if (word1 == null || word1.length() == 0 || word2 == null || word2.length() == 0) {
            return (word1 == null ? 0 : word1.length()) + (word2 == null ? 0 : word2.length());
        }
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int add = dp[i - 1][j] + 1;
                int del = dp[i][j - 1] + 1;
                int rep = dp[i - 1][j - 1];
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    rep += 1;
                }
                dp[i][j] = Math.min(add, Math.min(del, rep));
            }
        }
        return dp[m][n];
    }
}

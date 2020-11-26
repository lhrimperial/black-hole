package com.github.black.hole.base.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
public class Solution5 {

    public static void main(String[] args) {
        String input = "babad";
        String ans = longestPalindrome(input);
        System.out.println(ans);
    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * <p>示例 1：
     *
     * <p>输入: "babad" 输出: "bab" 注意: "aba" 也是一个有效答案。
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i + l < n; ++i) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
            System.out.println(ans);
        }
        return ans;
    }
}

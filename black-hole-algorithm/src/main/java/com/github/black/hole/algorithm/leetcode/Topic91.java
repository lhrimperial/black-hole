package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/1
 */
public class Topic91 {
    /** 91. 解码方法 */
    public static void main(String[] args) {
        String s = "06";
        System.out.println(numDecodings(s));
    }

    public static int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        char[] chars = s.toCharArray();
        for (int i = 1; i <= len; i++) {
            if (chars[i - 1] != '0') {
                dp[i] = dp[i - 1];
            }
            if (i >= 2 && (chars[i - 2] == '1' || chars[i - 2] == '2' && chars[i - 1] <= '6')) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[len];
    }
}

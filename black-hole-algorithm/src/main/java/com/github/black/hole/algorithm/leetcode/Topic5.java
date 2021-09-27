package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic5 {
    /** 5. 最长回文子串 */
    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome(s));
        System.out.println(longestPalindrome1(s));
    }

    public static String longestPalindrome1(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int begin = 0, maxLen = 1, len = s.length();
        boolean[][] dp = new boolean[len][len];
        char[] chars = s.toCharArray();

        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                if (chars[i] != chars[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        int begin = 0, maxLen = 1, len = s.length();
        char[] chars = s.toCharArray();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && isPalindrome(chars, i, j)) {
                    begin = i;
                    maxLen = j - i + 1;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    public static boolean isPalindrome(char[] chars, int left, int right) {
        while (left < right) {
            if (chars[left++] != chars[right--]) {
                return false;
            }
        }
        return true;
    }
}

package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/3
 */
public class Topic43 {
    /** 43. 字符串相乘 */
    public static void main(String[] args) {
        String num1 = "123", num2 = "456";
        System.out.println(multiply(num1, num2));
    }

    public static String multiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int n = num1.length(), m = num2.length();
        int[] ans = new int[n + m];
        for (int i = n - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = m - 1; j >= 0; j--) {
                int y = num2.charAt(j) - '0';
                int s = ans[i + j + 1] + x * y;
                ans[i + j + 1] = s % 10;
                ans[i + j] += s / 10;
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ans.length; i++) {
            if (i == 0 && ans[i] == 0) {
                continue;
            }
            res.append(ans[i]);
        }
        return res.toString();
    }
}

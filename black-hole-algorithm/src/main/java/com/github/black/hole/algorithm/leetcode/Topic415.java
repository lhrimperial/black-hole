package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/26
 */
public class Topic415 {
    /**
     * 字符串相加
     * @param num1
     * @param num2
     * @return
     */

    public String addStrings(String num1, String num2) {
        if (num1 == null || num2 == null) {
            return num1 == null ? num2 : num1;
        }
        int l1 = num1.length() - 1, l2 = num2.length() - 1;
        int x = 0, y = 0, carry = 0;
        StringBuilder sb = new StringBuilder();
        while (l1 >= 0 || l2 >= 0) {
            x = l1 >= 0 ? num1.charAt(l1) - '0' : 0;
            y = l2 >= 0 ? num2.charAt(l2) - '0' : 0;
            int s = x + y + carry;
            sb.append(s % 10);
            carry = s / 10;
            l1--;
            l2--;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}

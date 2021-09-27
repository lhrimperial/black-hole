package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author hairen.long
 * @date 2021/9/27
 */
public class Topic224 {
    /** 224. 基本计算器 */
    public static void main(String[] args) {
        String s = "1-(4+5+2)-3+(16+8)";
        System.out.println(calculate(s));
    }

    public static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Deque<Integer> ops = new ArrayDeque<>();
        int sign = 1;
        ops.push(sign);

        int i = 0, res = 0, len = s.length();
        while (i < len) {
            char ch = s.charAt(i);
            if (ch == ' ') {
                i++;
            } else if (ch == '+') {
                sign = ops.peek();
                i++;
            } else if (ch == '-') {
                sign = -ops.peek();
                i++;
            } else if (ch == '(') {
                ops.push(sign);
                i++;
            } else if (ch == ')') {
                ops.pop();
                i++;
            } else {
                long num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                res += sign * num;
            }
        }
        return res;
    }
}

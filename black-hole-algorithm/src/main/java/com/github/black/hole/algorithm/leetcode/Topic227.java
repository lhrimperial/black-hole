package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author hairen.long
 * @date 2021/9/27
 */
public class Topic227 {

    /**
     * 227. 基本计算器 II 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     *
     * <p>整数除法仅保留整数部分。
     */
    public static void main(String[] args) {
        String s = "3+2*2";
        System.out.println(calculate(s));
    }

    public static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0, len = s.length();
        char preSign = '+';
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                num = num * 10 + ch - '0';
            }
            if (!Character.isDigit(ch) && ch != ' ' || i == len - 1) {
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);
                        break;
                    default:
                }
                preSign = ch;
                num = 0;
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.pop();
        }
        return ans;
    }
}

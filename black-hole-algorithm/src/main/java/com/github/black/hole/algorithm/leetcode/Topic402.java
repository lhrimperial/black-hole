package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author hairen.long
 * @date 2021/9/27
 */
public class Topic402 {
    /** 402. 移掉 K 位数字 */
    public static void main(String[] args) {
        String num = "1432219";
        int k = 3;
        System.out.println(removeKdigits(num, k));
    }

    public static String removeKdigits(String num, int k) {
        Deque<Character> queue = new ArrayDeque<>();
        for (int i = 0, len = num.length(); i < len; i++) {
            char ch = num.charAt(i);
            while (!queue.isEmpty() && k > 0 && ch < queue.peekLast()) {
                queue.pollLast();
                k--;
            }
            queue.offer(ch);
        }
        for (int i = 0; i < k; i++) {
            queue.pollLast();
        }
        StringBuilder sb = new StringBuilder();
        boolean zeroHead = true;
        while (!queue.isEmpty()) {
            char ch = queue.pollFirst();
            if (zeroHead && ch == '0') {
                continue;
            }
            zeroHead = false;
            sb.append(ch);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}

package com.github.black.hole.base.algorithm.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class Solution20 {
    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * <p>有效字符串需满足：
     *
     * <p>左括号必须用相同类型的右括号闭合。 左括号必须以正确的顺序闭合。 注意空字符串可被认为是有效字符串。
     */
    public static void main(String[] args) {
        String str = "{[({{}})]}";
        boolean res = isValid(str);
        System.out.println(res);
    }

    public static boolean isValid(String s) {
        int len = s.length();
        // 长度为奇数
        if ((len & 1) == 1) {
            return false;
        }
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            Character ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(stack.peek(), pairs.get(ch))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }

        return true;
    }
}

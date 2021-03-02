package com.github.black.hole.algorithm.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/2/12
 */
public class Topic20 {

    /** 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 */
    public static void main(String[] args) {
        String s = "()[]{[]}";
        System.out.println("isValid = " + isValid2(s));
    }

    public static boolean isValid2(String s) {
        if (s == null || s == "" || (s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> bracketMap =
                new HashMap<Character, Character>(3) {
                    {
                        put('(', ')');
                        put('[', ']');
                        put('{', '}');
                    }
                };
        Deque<Character> stack = new LinkedList<>();
        for (Character ch : s.toCharArray()) {
            if (bracketMap.containsKey(ch)) {
                stack.addLast(ch);
            } else if (stack.isEmpty() || !Objects.equals(bracketMap.get(stack.removeLast()), ch)) {
                return false;
            }
        }
        return stack.size() == 0;
    }

    public static boolean isValid(String s) {
        if (s == null || s == "" || (s.length() & 1) == 1) {
            return false;
        }
        Map<Character, Character> bracketMap =
                new HashMap<Character, Character>(3) {
                    {
                        put(')', '(');
                        put(']', '[');
                        put('}', '{');
                    }
                };
        Deque<Character> stack = new LinkedList<>();
        for (Character ch : s.toCharArray()) {
            if (bracketMap.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(stack.peek(), bracketMap.get(ch))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }
}

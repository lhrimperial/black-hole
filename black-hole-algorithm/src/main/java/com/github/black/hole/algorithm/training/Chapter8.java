package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/8/20
 */
public class Chapter8 {

    /** 20 有效括号 */
    public static void main(String[] args) {
        System.out.println(isValid2("(){[()]}"));
        System.out.println(isValid2("(){[)(]}"));
    }

    public static boolean isValid2(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Deque<Character> stack = new ArrayDeque<>();
        for (Character ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                stack.push(ch);
            } else if (stack.isEmpty() || !Objects.equals(map.get(stack.pop()), ch)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();
        for (Character ch : s.toCharArray()) {
            if (map.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(stack.peek(), map.get(ch))) {
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

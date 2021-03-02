package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/2/14
 */
public class Topic242 {

    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     *
     * <p>输入: s = "anagram", t = "nagaram" 输出: true
     */
    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println("result = " + isAnagram2(s, t));
    }

    public static boolean isAnagram2(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> charMap = new HashMap<>();
        for (char ch : s.toCharArray()) {
            charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
        }
        for (Character ch : t.toCharArray()) {
            charMap.put(ch, charMap.getOrDefault(ch, 0) - 1);
            if (charMap.get(ch) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串排序后相等
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram1(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        Arrays.sort(sChar);
        Arrays.sort(tChar);
        return Arrays.equals(sChar, tChar);
    }
}

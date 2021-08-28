package com.github.black.hole.algorithm.training;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class Chapter14 {

    /** 242 有效的字母异位 */
    public static void main(String[] args) {
        System.out.println(isAnagram1("abcdef", "fedcba"));
        System.out.println(isAnagram1("abcdef", "fedcbaq"));
    }

    public static boolean isAnagram2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (Character ch : s1.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (Character ch : s2.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) - 1);
            if (map.get(ch) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagram1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }
}

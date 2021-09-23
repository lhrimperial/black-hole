package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic567 {

    /**
     * 567. 字符串的排列
     *
     * <p>给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。
     *
     * <p>换句话说，s1 的排列之一是 s2 的 子串 。
     *
     * <p>
     *
     * <p>示例 1：
     *
     * <p>输入：s1 = "ab" s2 = "eidbaooo" 输出：true 解释：s2 包含 s1 的排列之一 ("ba").
     */
    public static void main(String[] args) {
        String s1 = "ab", s2 = "eidboaoo";
        System.out.println(checkInclusion(s2, s1));
    }

    public static boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return false;
        }
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : s2.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, valid = 0;
        while (right < s1.length()) {
            char rc = s1.charAt(right++);
            if (need.containsKey(rc)) {
                window.put(rc, window.getOrDefault(rc, 0) + 1);
                if (window.get(rc).equals(need.get(rc))) {
                    valid++;
                }
            }

            while (right - left >= s2.length()) {
                if (need.size() == valid) {
                    return true;
                }
                char lc = s1.charAt(left++);
                if (need.containsKey(lc)) {
                    if (window.get(lc).equals(need.get(lc))) {
                        valid--;
                    }
                    window.put(lc, window.getOrDefault(lc, 0) - 1);
                }
            }
        }
        return false;
    }
}

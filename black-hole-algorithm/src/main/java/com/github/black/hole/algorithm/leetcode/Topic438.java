package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic438 {

    /**
     * 438. 找到字符串中所有字母异位词 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     *
     * <p>异位词 指字母相同，但排列不同的字符串。
     *
     * <p>
     *
     * <p>示例 1:
     *
     * <p>输入: s = "cbaebabacd", p = "abc" 输出: [0,6] 解释: 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。 起始索引等于 6
     * 的子串是 "bac", 它是 "abc" 的异位词。
     */
    public static void main(String[] args) {
        String s = "cbaebabacd", p = "abc";
        System.out.println(findAnagrams(s, p).toString());
    }

    public static List<Integer> findAnagrams(String s, String p) {
        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            return new ArrayList<>();
        }
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : p.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, valid = 0;
        List<Integer> res = new ArrayList<>();
        while (right < s.length()) {
            char rc = s.charAt(right++);
            if (need.containsKey(rc)) {
                window.put(rc, window.getOrDefault(rc, 0) + 1);
                if (window.get(rc).equals(need.get(rc))) {
                    valid++;
                }
            }
            if (right - left >= p.length()) {
                if (valid == need.size()) {
                    res.add(left);
                }
                char lc = s.charAt(left++);
                if (need.containsKey(lc)) {
                    if (need.get(lc).equals(window.get(lc))) {
                        valid--;
                    }
                    window.put(lc, window.get(lc) - 1);
                }
            }
        }
        return res;
    }
}

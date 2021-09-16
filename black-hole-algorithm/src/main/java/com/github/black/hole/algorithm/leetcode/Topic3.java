package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/2/22
 */
public class Topic3 {

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * <p>输入: s = "abcabcbb" 输出: 3 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    public static void main(String[] args) {
        String s = "abcadbcdbb";
        int result = lengthOfLongestSubstring1(s);
        System.out.println(result);
    }

    public static int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        // 左边界
        int left = 0, ans = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (map.containsKey(s.charAt(i))) {
                // 左边界移除重复字符
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        Set<Character> set = new HashSet<>();
        // 滑动窗口右边界
        int rk = -1, ans = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (i > 0) {
                set.remove(s.charAt(i - 1));
            }
            // 右边界边界加1 小于长度 且 集合不包含字符
            while (rk + 1 < len && !set.contains(s.charAt(rk + 1))) {
                set.add(s.charAt(rk + 1));
                rk++;
            }
            // 右边界 - 左边界 + 1
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }
}

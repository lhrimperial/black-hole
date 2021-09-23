package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/21
 */
public class Topic76 {
    /**
     * 76. 最小覆盖子串
     *
     * <p>给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     *
     * <p>
     *
     * <p>注意：
     *
     * <p>对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     *
     * <p>示例 1：
     *
     * <p>输入：s = "ADOBECODEBANC", t = "ABC" 输出："BANC"
     */
    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABDC"));
        System.out.println(minWindow1("ADOBECODEBANC", "ABDC"));
    }

    public static String minWindow1(String s, String t) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();
        for (char ch : t.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }
        String ans = "";
        int len = s.length() + 1, cnt = 0;
        // 有多少个元素符合
        for (int right = 0, left = 0; right < s.length(); right++) {
            char rc = s.charAt(right);
            window.put(rc, window.getOrDefault(rc, 0) + 1);
            if (need.containsKey(rc) && window.get(rc) <= need.get(rc)) {
                cnt++;
            }
            while (left < right
                    && (!need.containsKey(s.charAt(left))
                            || window.get(s.charAt(left)) > need.get(s.charAt(left)))) {
                char lc = s.charAt(left);
                int count = window.get(lc) - 1;
                window.put(lc, count);
                left++;
            }
            if (cnt == t.length() && right - left + 1 < len) {
                len = right - left + 1;
                ans = s.substring(left, right + 1);
            }
        }
        return ans;
    }

    public static String minWindow(String s, String t) {
        if (s == null || s.length() == 0) {
            return "";
        }
        Map<Character, Integer> need = new HashMap<>();
        for (char ch : t.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0, start = 0, len = s.length() + 1;

        while (right < s.length()) {
            char rc = s.charAt(right++);
            // 进行窗口内数据的一系列更新
            if (need.containsKey(rc)) {
                window.put(rc, window.getOrDefault(rc, 0) + 1);
                if (need.get(rc).equals(window.get(rc))) {
                    valid++;
                }
            }

            // 判断左侧窗口是否要收缩
            while (valid == need.size()) {
                // 在这里更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // left 将移出窗口的字符
                char lc = s.charAt(left++);
                if (need.containsKey(lc)) {
                    if (window.get(lc).equals(need.get(lc))) {
                        valid--;
                    }
                    window.put(lc, window.getOrDefault(lc, 0) - 1);
                }
            }
        }
        return len == s.length() + 1 ? "" : s.substring(start, start + len);
    }
}

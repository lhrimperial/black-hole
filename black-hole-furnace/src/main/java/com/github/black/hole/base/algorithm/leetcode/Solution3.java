package com.github.black.hole.base.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2020/10/28
 */
public class Solution3 {

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * @param args
     */
    public static void main(String[] args) {
        int res = lengthOfLongestSubstring("abcabcbb");
        System.out.println(res);
    }

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> charSet = new HashSet<>();

        int rk = -1, ans = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (i != 0) {
                charSet.remove(s.charAt(i - 1));
            }
            while (rk + 1 < len && !charSet.contains(s.charAt(rk + 1))) {
                charSet.add(s.charAt(rk + 1));
                rk++;
            }
            ans = Math.max(ans, rk - i + 1);
            System.out.println(s.substring(i, rk + 1));
        }
        return ans;
    }
}

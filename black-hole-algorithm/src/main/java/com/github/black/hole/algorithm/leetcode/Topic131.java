package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic131 {
    /** 131. 分割回文串 */
    public static void main(String[] args) {
        String s = "aabb";
        System.out.println(partition(s).toString());
    }

    public static List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        int len = s.length();
        boolean[][] dp = new boolean[len][len];

        for (int right = 0; right < len; right++) {
            // 注意：left <= right 取等号表示 1 个字符的时候也需要判断
            for (int left = 0; left <= right; left++) {
                if (s.charAt(left) == s.charAt(right)
                        && (right - left <= 2 || dp[left + 1][right - 1])) {
                    dp[left][right] = true;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }

        List<List<String>> res = new ArrayList<>();
        List<String> sub = new ArrayList<>();
        dfs(s, len, 0, dp, sub, res);
        return res;
    }

    private static void dfs(
            String s, int n, int i, boolean[][] dp, List<String> sub, List<List<String>> res) {
        if (n == i) {
            res.add(new ArrayList<>(sub));
            return;
        }
        for (int j = i; j < n; j++) {
            if (dp[i][j]) {
                sub.add(s.substring(i, j + 1));
                dfs(s, n, j + 1, dp, sub, res);
                sub.remove(sub.size() - 1);
            }
        }
    }
}

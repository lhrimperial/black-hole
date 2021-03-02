package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/2/21
 */
public class Topic77 {

    /**
     * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     *
     * <p>输入: n = 4, k = 2 输出: [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
     */
    public static void main(String[] args) {
        List<List<Integer>> result = combine(4, 2);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> combine(int n, int k) {
        if (k < 0 || n < k) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(n, k, 1, path, result);
        return result;
    }

    public static void dfs(
            int n, int k, int begin, Deque<Integer> path, List<List<Integer>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        int upper = n - (k - path.size()) + 1;
        for (int i = begin; i <= upper; i++) {
            path.addLast(i);
            dfs(n, k, i + 1, path, result);
            path.removeLast();
        }
    }
}

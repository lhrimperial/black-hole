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
public class Topic39 {

    /**
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * <p>candidates 中的数字可以无限制重复被选取。
     *
     * <p>输入：candidates = [2,3,6,7], target = 7, 所求解集为： [ [7], [2,2,3] ]
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 6, 7};
        List<List<Integer>> result = combinationSum(arr, 7);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length < 1) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates, candidates.length, target, 0, path, result);
        return result;
    }

    private static void dfs(
            int[] candidates,
            int len,
            int target,
            int begin,
            Deque<Integer> path,
            List<List<Integer>> result) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < len; i++) {
            path.addLast(candidates[i]);
            dfs(candidates, len, target - candidates[i], i, path, result);
            path.removeLast();
        }
    }
}

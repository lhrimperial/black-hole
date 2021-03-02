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
public class Topic46 {

    /**
     * 全排列
     *
     * <p>给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<List<Integer>> result = test(arr);
        System.out.println(result.toString());
    }

    /**
     * 深度优先 回溯 递归
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> test(int[] nums) {
        if (nums == null || nums.length < 1) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[nums.length];
        dfsTest(nums, nums.length, 0, used, path, result);
        return result;
    }

    private static void dfsTest(
            int[] nums,
            int len,
            int depth,
            boolean[] used,
            Deque<Integer> path,
            List<List<Integer>> result) {
        if (depth == len) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;
                dfsTest(nums, len, depth + 1, used, path, result);
                path.removeLast();
                used[i] = false;
            }
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length < 1) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        dfs(nums, nums.length, 0, used, path, result);
        return result;
    }

    private static void dfs(
            int[] nums,
            int len,
            int depth,
            boolean[] used,
            List<Integer> path,
            List<List<Integer>> result) {
        if (depth == len) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;
                dfs(nums, len, depth + 1, used, path, result);

                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}

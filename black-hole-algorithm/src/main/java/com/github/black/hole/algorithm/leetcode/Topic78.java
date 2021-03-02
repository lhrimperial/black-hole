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
public class Topic78 {

    /**
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * <p>解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     *
     * <p>输入：nums = [1,2,3] 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = subsets(nums);
        System.out.println(result);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length < 1) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(nums, nums.length, 0, path, result);
        return result;
    }

    private static void dfs(
            int[] nums, int len, int curr, Deque<Integer> path, List<List<Integer>> result) {
        if (curr == len) {
            result.add(new ArrayList<>(path));
            return;
        }

        path.addLast(nums[curr]);
        dfs(nums, len, curr + 1, path, result);
        path.removeLast();
        dfs(nums, len, curr + 1, path, result);
    }
}
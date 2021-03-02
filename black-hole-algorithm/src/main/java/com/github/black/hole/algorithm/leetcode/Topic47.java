package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/2/21
 */
public class Topic47 {

    /**
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     *
     * <p>输入：nums = [1,1,2] 输出： [[1,1,2], [1,2,1], [2,1,1]]
     */
    public static void main(String[] args) {
        int[] arr = {1, 1, 2};
        // 方法一：使用Set保存结果集
        // 方法二：使用排序+减支
        List<List<Integer>> result = test(arr);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> test(int[] nums) {
        if (nums == null || nums.length < 1) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
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
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            path.addLast(nums[i]);
            used[i] = true;
            dfsTest(nums, len, depth + 1, used, path, result);
            path.removeLast();
            used[i] = false;
        }
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length < 1) {
            return Collections.emptyList();
        }
        // 排序
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        dfs(nums, nums.length, 0, used, path, result);
        return new ArrayList<>(result);
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
        // 减支
        for (int i = 0; i < len; i++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            dfs(nums, len, depth + 1, used, path, result);
            used[i] = false;
            path.remove(path.size() - 1);
        }
    }
}

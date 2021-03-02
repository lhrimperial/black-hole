package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/2/21
 */
public class Topic40 {

    /**
     * 给定一个数组candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
     *
     * <p>candidates中的每个数字在每个组合中只能使用一次。
     *
     * <p>输入: candidates = [10,1,2,7,6,1,5], target = 8, 所求解集为: [ [1, 7], [1, 2, 5], [2, 6], [1, 1,
     * 6] ]
     */
    public static void main(String[] args) {
        int[] arr = {10, 1, 2, 7, 6, 1, 5};
        List<List<Integer>> result = combinationSum2(arr, 8);
        System.out.println(result);
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length < 1) {
            return Collections.emptyList();
        }
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(candidates, candidates.length, 0, target, path, result);
        return result;
    }

    private static void dfs(
            int[] nums,
            int len,
            int begin,
            int target,
            Deque<Integer> path,
            List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < len; i++) {
            if (target - nums[i] < 0) {
                break;
            }
            if (i > begin && nums[i] == nums[i - 1]) {
                continue;
            }

            path.addLast(nums[i]);
            dfs(nums, len, i + 1, target - nums[i], path, result);
            path.removeLast();
        }
    }
}

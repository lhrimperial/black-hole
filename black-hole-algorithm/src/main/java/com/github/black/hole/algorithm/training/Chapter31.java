package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter31 {
    /** 回溯+剪枝 */
    public static void main(String[] args) {
        int[] arr = {1, 1, 3};
        List<List<Integer>> result = permute(arr);
        System.out.println(result.toString());
        System.out.println(permuteNoRepeat(new int[] {1, 2, 1}).toString());

        int[] arr1 = {2, 6, 2, 3, 7};
        List<List<Integer>> result1 = combinationSum(arr1, 7);
        System.out.println(result1.toString());

        System.out.println(combine(4, 2));
    }

    public static List<List<Integer>> combine(int n, int k) {
        if (k < 0 || n < k) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfsCombine(n, k, 1, path, result);
        return result;
    }

    public static void dfsCombine(
            int n, int k, int begin, Deque<Integer> path, List<List<Integer>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        int upper = n - (k - path.size()) + 1;
        for (int i = begin; i <= upper; i++) {
            path.addLast(i);
            dfsCombine(n, k, i + 1, path, result);
            path.removeLast();
        }
    }

    public static List<List<Integer>> combinationSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfsCombination(arr, 0, target, path, result);
        return result;
    }

    public static void dfsCombination(
            int[] arr, int begin, int target, Deque<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < arr.length; i++) {
            if (target - arr[i] < 0) {
                break;
            }
            if (i > begin && arr[i] == arr[i - 1]) {
                continue;
            }
            path.addLast(arr[i]);
            dfsCombination(arr, i + 1, target - arr[i], path, result);
            path.removeLast();
        }
    }

    public static List<List<Integer>> permuteNoRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return Collections.emptyList();
        }
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[arr.length];
        dfsNoRepeat(arr, arr.length, 0, used, path, result);
        return result;
    }

    public static void dfsNoRepeat(
            int[] arr,
            int len,
            int depth,
            boolean[] used,
            Deque<Integer> path,
            List<List<Integer>> result) {
        if (len == depth) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && arr[i] == arr[i - 1] && used[i - 1]) {
                continue;
            }
            used[i] = true;
            path.addLast(arr[i]);
            dfsNoRepeat(arr, len, depth + 1, used, path, result);
            used[i] = false;
            path.removeLast();
        }
    }

    public static List<List<Integer>> permute(int[] arr) {
        if (arr == null || arr.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[arr.length];
        dfs(arr, arr.length, 0, used, path, result);
        return result;
    }

    public static void dfs(
            int[] arr,
            int len,
            int depth,
            boolean[] used,
            Deque<Integer> path,
            List<List<Integer>> result) {
        if (len == depth) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                used[i] = true;
                path.addLast(arr[i]);
                dfs(arr, len, depth + 1, used, path, result);

                used[i] = false;
                path.removeLast();
            }
        }
    }
}

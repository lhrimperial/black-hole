package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/28
 */
public class Topic120 {

    /**
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     *
     * <p>每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i
     * ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     */
    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));
        System.out.println(minimumTotal1(triangle));
        System.out.println(minimumTotal2(triangle));
        System.out.println(minimumTotal3(triangle));
    }

    public static int minimumTotal3(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        int n = triangle.size();
        int[] dp = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public static int minimumTotal2(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        int n = triangle.size();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }

    public static int minimumTotal1(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        return dfsMinimumTotal1(triangle, 0, 0);
    }

    public static int dfsMinimumTotal1(List<List<Integer>> triangle, int i, int j) {
        if (triangle.size() == i) {
            return 0;
        }
        return Math.min(
                        dfsMinimumTotal1(triangle, i + 1, j),
                        dfsMinimumTotal1(triangle, i + 1, j + 1))
                + triangle.get(i).get(j);
    }
}

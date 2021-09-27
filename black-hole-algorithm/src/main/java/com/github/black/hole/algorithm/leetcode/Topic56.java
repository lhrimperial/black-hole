package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic56 {

    /**
     * 56. 合并区间 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi]
     * 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
     */
    public static void main(String[] args) {}

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        int[][] res = new int[intervals.length][2];
        int idx = -1;
        for (int[] ints : intervals) {
            if (idx == -1 || ints[0] > res[idx][1]) {
                res[++idx] = ints;
            } else {
                res[idx][1] = Math.max(res[idx][1], ints[1]);
            }
        }
        return Arrays.copyOf(res, idx + 1);
    }
}

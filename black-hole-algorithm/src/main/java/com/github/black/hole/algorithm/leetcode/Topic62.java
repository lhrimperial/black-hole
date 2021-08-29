package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/28
 */
public class Topic62 {

    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     *
     * <p>机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     *
     * <p>问总共有多少条不同的路径？
     */
    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 7));
    }

    public static int uniquePaths(int m, int n) {
        if (m == 0 && n == 0) {
            return 0;
        }
        int[][] path = new int[m][n];
        for (int i = 0; i < m; i++) {
            path[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            path[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                path[i][j] = path[i - 1][j] + path[i][j - 1];
            }
        }
        return path[m - 1][n - 1];
    }
}

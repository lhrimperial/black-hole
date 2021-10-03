package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/28
 */
public class Topic931 {
    /**
     * 931.下降路径最小和（中等） 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
     *
     * <p>下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。具体来说，位置
     * (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
     */
    public static void main(String[] args) {
        int[][] matrix = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        System.out.println(minFallingPathSum(matrix));
    }

    public static int minFallingPathSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int n = matrix.length;
        for (int r = n - 2; r >= 0; r--) {
            for (int c = 0; c < n; c++) {
                int num = matrix[r + 1][c];
                if (c > 0) {
                    num = Math.min(num, matrix[r + 1][c - 1]);
                }
                if (c + 1 < n) {
                    num = Math.min(num, matrix[r + 1][c + 1]);
                }
                matrix[r][c] += num;
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int num : matrix[0]) {
            ans = Math.min(num, ans);
        }
        return ans;
    }
}

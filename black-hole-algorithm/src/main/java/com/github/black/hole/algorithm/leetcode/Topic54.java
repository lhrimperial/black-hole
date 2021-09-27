package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/24
 */
public class Topic54 {
    /**
     * 54. 螺旋矩阵
     *
     * <p>给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     */
    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> res = spiralOrder(arr);
        System.out.println(res.toString());
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }
        int rows = matrix.length, col = matrix[0].length;
        List<Integer> result = new ArrayList<>(rows * col);
        int left = 0, right = col - 1, top = 0, bottom = rows - 1;
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            for (int i = top + 1; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            if (left < right && top < bottom) {
                for (int i = right - 1; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                for (int i = bottom - 1; i >= top + 1; i--) {
                    result.add(matrix[i][left]);
                }
            }

            left++;
            right--;
            top++;
            bottom--;
        }
        return result;
    }
}

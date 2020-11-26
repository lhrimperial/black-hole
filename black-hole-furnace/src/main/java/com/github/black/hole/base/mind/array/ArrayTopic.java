package com.github.black.hole.base.mind.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/11/17
 */
public class ArrayTopic {

    @Test
    public void test5() {
        int[][] nums = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
        System.out.println(kthSmallest(nums, 8));
    }

    /** 有序矩阵的 Kth Element ,二分查找解法 */
    public int kthSmallest(int[][] matrix, int k) {
        int m = matrix.length, n = matrix[0].length;
        int low = matrix[0][0], high = matrix[m - 1][n - 1];
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cnt = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n && matrix[i][j] <= mid; j++) {
                    cnt++;
                }
            }
            if (cnt < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    @Test
    public void test4() {
        int[][] nums = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(searchMatrix(nums, 15));
    }

    /** 有序矩阵查找 */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int m = matrix.length, n = matrix[0].length;
        int row = 0, col = n - 1;
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    @Test
    public void test3() {
        int[] nums = {0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0};
        System.out.println(findMaxConsecutiveOnes(nums));
    }

    /** 找出数组中最长的连续 1 */
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, curr = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            curr = nums[i] == 0 ? 0 : curr + 1;
            max = Math.max(max, curr);
        }
        return max;
    }

    @Test
    public void test2() {
        int[][] nums = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        int[][] result = matrixReshape(nums, 2, 6);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /** 改变矩阵维度 */
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int m = nums.length, n = nums[0].length;
        if (m * n != r * c) {
            return nums;
        }
        int[][] reshapedNums = new int[r][c];
        int index = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                reshapedNums[i][j] = nums[index / n][index % n];
                index++;
            }
        }
        return reshapedNums;
    }

    @Test
    public void test1() {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    /** 把数组中的 0 移到末尾 */
    public static void moveZeroes(int[] nums) {
        int idx = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] > 0) {
                nums[idx++] = nums[i];
            }
        }
        while (idx < nums.length) {
            nums[idx++] = 0;
        }
    }
}

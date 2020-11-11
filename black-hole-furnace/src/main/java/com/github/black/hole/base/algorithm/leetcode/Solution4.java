package com.github.black.hole.base.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/10/28
 */
public class Solution4 {

    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的中位数。
     *
     * <p>进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] num1 = {0, 1, 4, 5, 7, 8};
        int[] num2 = {2, 3, 6, 9};
        double res = findMedianSortArrays1(num1, num2);
        System.out.println(res);

        System.out.println(middle(num1));
    }

    public static double findMedianSortArrays1(int[] a, int[] b) {
        int m = a.length;
        int n = b.length;
        int left = -1, right = -1;
        int aStart = 0, bStart = 0;
        for (int i = 0, len = (m + n) / 2; i <= len; i++) {
            left = right;
            if (aStart < m && (bStart >= n || a[aStart] < b[bStart])) {
                right = a[aStart++];
            } else {
                right = b[bStart++];
            }
        }
        if (((m + n) & 1) == 0) {
            return (left + right) / 2D;
        } else {
            return right / 2D;
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null && nums2 == null) {
            return 0;
        }
        if (nums1 == null) {
            return findSingleArrayMid(nums2);
        }
        if (nums2 == null) {
            return findSingleArrayMid(nums1);
        }
        int[] arr = new int[nums1.length + nums2.length];
        int curr = 0, i1 = 0, i2 = 0;
        while (curr < arr.length && i1 < nums1.length && i2 < nums2.length) {
            if (nums1[i1] < nums2[i2]) {
                arr[curr++] = nums1[i1++];
            } else {
                arr[curr++] = nums2[i2++];
            }
        }
        while (i1 < nums1.length) {
            arr[curr++] = nums1[i1++];
        }
        while (i2 < nums2.length) {
            arr[curr++] = nums2[i2++];
        }
        System.out.println(Arrays.toString(arr));
        return findSingleArrayMid(arr);
    }

    private static double findSingleArrayMid(int[] nums) {
        int length = nums.length;
        int mid = length / 2;
        if (length % 2 == 0) {
            return (nums[mid] + nums[mid - 1]) / 2d;
        } else {
            return nums[mid];
        }
    }

    private static double middle(int[] arr) {
        if (arr == null) {
            return 0.0;
        }
        int len = arr.length;
        if ((len & 1) == 1) {
            return arr[len];
        }
        return (arr[(len >> 1) - 1] + arr[len >> 1]) / 2.0;
    }

    public double findMedianSortedArrays11(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median =
                    (getKthElement(nums1, nums2, midIndex1 + 1)
                                    + getKthElement(nums1, nums2, midIndex2 + 1))
                            / 2.0;
            return median;
        }
    }

    public int getKthElement(int[] nums1, int[] nums2, int k) {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */

        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }
}

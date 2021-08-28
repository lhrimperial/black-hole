package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/2/22
 */
public class Topic4 {

    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的中位数。
     *
     * <p>进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
     *
     * <p>输入：nums1 = [1,3], nums2 = [2] 输出：2.00000 解释：合并数组 = [1,2,3] ，中位数 2
     */
    public static void main(String[] args) {
        int[] num1 = {1, 3, 4, 9};
        int[] num2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(findMedianSortedArrays(num1, num2));
        System.out.println(findMedianSortedArrays1(num1, num2));
        System.out.println(findMedianSortedArrays2(num1, num2));
    }

    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        assert nums1 != null;
        assert nums2 != null;
        int n = nums1.length, m = nums2.length, len = (n + m);
        // 找到中间位置，为兼容奇偶个数所以找两个
        int left = len + 1 >> 1, right = len + 2 >> 1;
        // 左边第k小元素
        int leftKth = getKth(nums1, 0, n - 1, nums2, 0, m - 1, left);
        // 右边第k小元素
        int rightKth = getKth(nums1, 0, n - 1, nums2, 0, m - 1, right);
        return (leftKth + rightKth) / 2.0;
    }

    public static int getKth(
            int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        // 剩余数组的长度
        int len1 = end1 - start1, len2 = end2 - start2;
        // 如果前边数组比后边长，交换一下位置
        if (len1 > len2) {
            return getKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        // 当其中一个数组为空的时候，只需要取剩余数组第k个元素
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        // 当取第一小元素，比较两数组第一个元素的大小
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        // 二分查找，比较两数组的 k / 2 位置，把较小的一个数组的 k / 2 砍掉，位置要比长度少1
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;
        if (nums1[i] < nums2[j]) {
            // 把剩余的数组再带入计算第k = k - (i - start1 + 1) 小元素，原来的k 减去砍掉的部分
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        } else {
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        }
    }

    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        assert nums1 != null;
        assert nums2 != null;
        int n = nums1.length, m = nums2.length;
        int left = -1, right = -1, aStart = 0, bStart = 0;
        for (int i = 0, k = (n + m) >> 1; i <= k; i++) {
            left = right;
            if (aStart < n && (bStart >= m || nums1[aStart] < nums2[bStart])) {
                right = nums1[aStart++];
            } else {
                right = nums2[bStart++];
            }
        }
        if (((n + m) & 1) == 1) {
            return right;
        } else {
            return (left + right) / 2.0;
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null && nums2 == null) {
            return 0.0;
        }
        if (nums1 == null) {
            int len = nums2.length;
            if ((len & 1) == 1) {
                return nums2[len / 2 + 1];
            } else {
                return (nums2[len / 2 - 1] + nums2[len / 2]) / 2.0;
            }
        }
        if (nums2 == null) {
            int len = nums1.length;
            if ((len & 1) == 1) {
                return nums1[len / 2 + 1];
            } else {
                return (nums1[len / 2 - 1] + nums1[len / 2]) / 2.0;
            }
        }
        int[] nums = new int[nums1.length + nums2.length];
        int curr = 0, l1 = 0, l2 = 0;
        while (l1 < nums1.length && l2 < nums2.length) {
            if (nums1[l1] < nums2[l2]) {
                nums[curr++] = nums1[l1++];
            } else {
                nums[curr++] = nums2[l2++];
            }
        }
        while (l1 < nums1.length) {
            nums[curr++] = nums1[l1++];
        }
        while (l2 < nums2.length) {
            nums[curr++] = nums2[l2++];
        }
        int len = nums.length;
        if ((len & 1) == 1) {
            return nums[len / 2 + 1];
        } else {
            return (nums[len / 2 - 1] + nums[len / 2]) / 2.0;
        }
    }
}

package com.github.black.hole.algorithm.leetcode;

import java.util.PriorityQueue;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic215 {

    /**
     * 215. 数组中的第K个最大元素
     *
     * <p>给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     *
     * <p>请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     */
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println(findKthLargest(arr, 2));
    }

    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        for (int i = 0, len = nums.length; i < len; i++) {
            if (queue.size() < k) {
                queue.offer(nums[i]);
            } else if (nums[i] > queue.peek()) {
                queue.poll();
                queue.offer(nums[i]);
            }
        }
        return queue.peek();
    }
}

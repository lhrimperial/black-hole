package com.github.black.hole.algorithm.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/2/12
 */
public class Topic239 {

    /**
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * <p>返回滑动窗口中的最大值。
     */
    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, -4, 5, 3, 6, 7};
        int[] result = maxSlidingWindow3(arr, 3);
        System.out.println(Arrays.toString(result));
    }

    public static int[] maxSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        int[] ans = new int[nums.length - k + 1];
        // 使用双端队列保存下标，队列最最左边保存窗口中最大值的下标
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            // 队列不为空，且队列最左端值小于当前值，移除队列元素
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            // 把下标放入队列
            deque.offerLast(i);
            // 保证队列元素个数在窗口范围内
            if (deque.peek() <= i - k) {
                deque.poll();
            }
            // 当遍历到窗口右边界市保存结果
            if (i >= k - 1) {
                ans[i - k + 1] = nums[deque.peek()];
            }
        }
        return ans;
    }

    /**
     * 优先队列
     *
     * <p>时间复杂度 O(NlogN)
     *
     * <p>空间复杂度 O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        // 建立大点堆
        Queue<Integer> maxQueue = new PriorityQueue<>(3, (v1, v2) -> v2 - v1);
        int len = nums.length, index = 0;
        int[] ans = new int[len - k + 1];
        for (int i = 0; i < len; i++) {
            // 移除第一个元素
            if (maxQueue.size() >= k) {
                maxQueue.remove(nums[i - k]);
            }
            maxQueue.offer(nums[i]);
            if (i >= k - 1) {
                ans[index++] = maxQueue.peek();
            }
        }
        return ans;
    }

    /**
     * 暴力解法
     *
     * <p>时间复杂度 O(N^2)
     *
     * <p>空间复杂度 O(N)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        int[] ans = new int[nums.length - k + 1];
        for (int i = 0, len = ans.length; i < len; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            ans[i] = max;
        }
        return ans;
    }
}

package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class Chapter12 {
    /** 239 滑动窗口最大值 */
    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, -4, 5, 3, 6, 7};
        System.out.println(Arrays.toString(maxSlidingWindow1(arr, 3)));
        System.out.println(Arrays.toString(maxSlidingWindow2(arr, 3)));
        System.out.println(Arrays.toString(maxSlidingWindow3(arr, 3)));
    }

    public static int[] maxSlidingWindow3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0, len = arr.length; i < len; i++) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            if (i >= k - 1) {
                ans[i - k + 1] = arr[queue.peek()];
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        Queue<Integer> maxQueue = new PriorityQueue<>(3, (v1, v2) -> v2 - v1);
        for (int i = 0, len = arr.length; i < len; i++) {
            if (maxQueue.size() >= k) {
                maxQueue.remove(arr[i - k]);
            }
            maxQueue.offer(arr[i]);
            if (i >= k - 1) {
                ans[i - k + 1] = maxQueue.peek();
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow1(int[] arr, int k) {
        if (arr == null || arr.length == 1) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        for (int i = 0, len = ans.length; i < len; i++) {
            int max = arr[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }
            ans[i] = max;
        }
        return ans;
    }
}

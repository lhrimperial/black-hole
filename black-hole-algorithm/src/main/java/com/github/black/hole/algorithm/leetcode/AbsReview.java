package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class AbsReview {

    public static void main(String[] args) {
        int[] arr = {1, 3, -1, -3, -4, 5, 3, 6, 7};
        int[] result = maxSlidingWindow(arr, 3);
        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString(maxSlidingWindow1(arr, 3)));
        System.out.println(Arrays.toString(maxSlidingWindow2(arr, 3)));
    }

    public static int[] maxSlidingWindow2(int[] arr, int k) {
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
            if (i >= k - 1){
                ans[i - k + 1] = arr[queue.peek()];
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        Queue<Integer> queue = new PriorityQueue<>(k, (v1, v2) -> v2 - v1);
        int[] ans = new int[arr.length - k + 1];
        for (int i = 0, len = arr.length; i < len; i++) {
            if (queue.size() >= k) {
                queue.remove(arr[i - k]);
            }
            queue.offer(arr[i]);
            if (i >= k - 1) {
                ans[i - k + 1] = queue.peek();
            }
        }
        return ans;
    }

    public static int[] maxSlidingWindow(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ans = new int[arr.length - k + 1];
        for (int i = 0; i < ans.length; i++) {
            int max = arr[i];
            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }
            ans[i] = max;
        }
        return ans;
    }
}

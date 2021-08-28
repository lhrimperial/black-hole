package com.github.black.hole.algorithm.training;

import com.github.black.hole.algorithm.leetcode.Topic703;

import java.util.PriorityQueue;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class Chapter11 {

    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest( new int[] {4, 5, 8, 2, 10},3);
        System.out.println("第k大：" + kthLargest.getKMax());
    }

    /** top703 */

    private static class KthLargest {
        private int k;
        private PriorityQueue<Integer> queue;

        public KthLargest(int[] arr, int k) {
            this.k = k;
            this.queue = new PriorityQueue<>(k);
            for (int i : arr) {
                add(i);
            }
        }

        private void add(int val) {
            if (queue.size() < k) {
                queue.offer(val);
            } else if (queue.peek() < val) {
                queue.poll();
                queue.offer(val);
            }
        }

        public int getKMax() {
            return queue.peek();
        }
    }
}

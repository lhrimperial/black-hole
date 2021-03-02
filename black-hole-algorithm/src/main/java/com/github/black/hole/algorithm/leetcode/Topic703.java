package com.github.black.hole.algorithm.leetcode;

import java.util.PriorityQueue;

/**
 * @author hairen.long
 * @date 2021/2/12
 */
public class Topic703 {

    /** 设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。 */
    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[] {4, 5, 8, 2, 10});
        System.out.println("第k大：" + kthLargest.getKMax());
    }

    private static class KthLargest {
        int k;
        PriorityQueue<Integer> queue;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.queue = new PriorityQueue<>(k);
            if (nums != null){
                for (int n : nums) {
                    add(n);
                }
            }
        }

        public int add(int val) {
            if (queue.size() < k) {
                queue.offer(val);
            } else if (queue.peek() < val) {
                queue.poll();
                queue.offer(val);
            }
            return queue.peek();
        }

        public int getKMax() {
            return queue.peek();
        }
    }
}

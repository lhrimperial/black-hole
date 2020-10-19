package com.github.black.hole.base.algorithm.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hairen.long
 * @date 2020/10/17
 */
public class FriendCircleSolution {

    private static AtomicInteger callCount;

    public static void main(String[] args) {
        int[][] friends = {{1, 1, 1, 0}, {1, 1, 0, 0}, {1, 0, 1, 0}, {0, 0, 0, 1}};
        int count = findCircleNum(friends);
        System.out.println(count);
    }

    /**
     * 查找一个人的所有朋友，再查找他朋友的朋友
     *
     * @param M
     * @return
     */
    public static int findCircleNum(int[][] M) {
        int res = 0;
        boolean[] visited = new boolean[M.length];
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                callCount = new AtomicInteger();
                dfs(M, i, visited);
                System.out.println(Arrays.toString(visited));
                res++;
            }
        }
        return res;
    }

    /**
     * DFS解法
     *
     * @param M
     * @param k
     * @param visited
     */
    private static void dfs(int[][] M, int k, boolean[] visited) {
        System.out.println(callCount.incrementAndGet());
        visited[k] = true;
        for (int i = 0; i < M.length; i++) {
            if (M[k][i] == 1 && !visited[i]) {
                dfs(M, i, visited);
            }
        }
    }

    public static int findCircleNum1(int[][] M) {
        int res = 0;
        boolean[] visited = new boolean[M.length];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                queue = new LinkedList<>();
                queue.add(i);
                bfs(M,queue,visited);
                System.out.println(queue);
                res++;
            }
        }
        return res;
    }


    /**
     * BFS解法
     *
     * @param M
     * @param queue
     * @param visited
     */
    private static void bfs(int[][] M, Queue<Integer> queue, boolean[] visited) {
        while (!queue.isEmpty()) {
            int k = queue.poll();
            visited[k] = true;
            for (int i = 0; i < M.length; i++) {
                if (!visited[i] && M[k][i] == 1) {
                    queue.add(i);
                }
            }
        }
    }
}

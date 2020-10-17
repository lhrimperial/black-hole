package com.github.black.hole.base.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2020/10/17
 */
public class FriendCircleSolution {

    public static void main(String[] args) {
        int[][] friends = {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};
        int count = findCircleNum(friends);
        System.out.println(count);
    }

    /**
     * @param M
     * @return
     */
    public static int findCircleNum(int[][] M) {
        int res = 0;
        boolean[] visited = new boolean[M.length];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                DFS(M, i, visited);
                /*
                queue = new LinkedList<>();
                queue.add(i);
                BFS(M,queue,visited);
                */
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
    private static void DFS(int[][] M, int k, boolean[] visited) {
        visited[k] = true;
        for (int i = 0; i < M.length; i++) {
            if (M[k][i] == 1 && !visited[i]) {
                DFS(M, i, visited);
            }
        }
    }

    /**
     * BFS解法
     *
     * @param M
     * @param queue
     * @param visited
     */
    private static void BFS(int[][] M, Queue<Integer> queue, boolean[] visited) {
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

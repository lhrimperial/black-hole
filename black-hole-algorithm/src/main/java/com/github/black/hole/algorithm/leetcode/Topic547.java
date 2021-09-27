package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic547 {

    /** 省份数量 */
    public static void main(String[] args) {
        int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        System.out.println(findCircleNum(isConnected));
    }

    public static int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }
        int ans = 0, len = isConnected.length;
        boolean[] visited = new boolean[len];
        for (int i = 0; i < len; i++) {
            if (!visited[i]) {
                ans++;
                dfs(i, isConnected, visited);
            }
        }
        return ans;
    }

    private static void dfs(int i, int[][] isConnected, boolean[] visited) {
        visited[i] = true;
        for (int j = 0, len = isConnected[0].length; j < len; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                dfs(j, isConnected, visited);
            }
        }
    }
}

package com.github.black.hole.base.algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2020/10/15
 */
public class Graph {
    /** 顶点个数 */
    private int v;
    /** 连接表 */
    private LinkedList<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        @SuppressWarnings({"rawtypes", "unchecked"})
        LinkedList<Integer>[] table = new LinkedList[v];

        this.adj = table;
        for (int i = 0; i < v; i++) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * 无向图一条边存两次
     *
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) {
        adj[s].add(t);
        adj[t].add(s);
    }

    /**
     * 广度优先
     *
     * @param s
     * @param t
     */
    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        // 节点是否访问过
        boolean[] visited = new boolean[v];
        // 自己被访问了，但是相连的节点还未被访问
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // 记录是从哪里遍历过来的
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }

        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    boolean found = false;

    public void dfs(int s, int t) {
        if (s == t) {
            return;
        }
        found = false;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found) {
            return;
        }
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); i++) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }
}

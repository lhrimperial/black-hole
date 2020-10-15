package com.github.black.hole.base.algorithm.graph;

import java.util.LinkedList;

/**
 * @author hairen.long
 * @date 2020/10/15
 */
public class Graph {
    /** 顶点个数 */
    private int v;
    /** 连接表 */
    private LinkedList<Integer>[] adj;

//    public Graph(int v) {
//        this.v = v;
//        this.adj = new LinkedList[v];
//        for (int i = 0; i < v; i++) {
//            this.adj[i] = new LinkedList<Integer>();
//        }
//    }

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
}

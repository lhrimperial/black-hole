package com.github.black.hole.base.algorithm.graph;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
public class GraphMain {

    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);
        System.out.println("广度优先：");
        graph.bfs(0, 6);

        System.out.println();
        System.out.println("深度优先");
        graph.dfs(0, 6);
    }
}

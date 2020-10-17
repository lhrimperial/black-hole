package com.github.black.hole.base.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2020/10/17
 */
public class FriendCircleSolution2 {

    public static void main(String[] args) {
        int[][] friends = {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};
        int count = findCircleNum(friends);
        System.out.println(count);
    }

    static class UnionFind {
        private int[] parent, rank;
        private int count;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        // 压缩路径
        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            } else if (rank[rootQ] < rank[rootP]) {
                parent[rootQ] = rootP;
            } else {
                parent[rootP] = rootQ;
                rank[rootQ]++;
            }
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    public static int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind circle = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 1 && i != j) {
                    circle.union(i, j);
                }
            }
        }
        return circle.getCount();
    }
}

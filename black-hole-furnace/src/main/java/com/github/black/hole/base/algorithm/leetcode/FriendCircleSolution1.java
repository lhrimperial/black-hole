package com.github.black.hole.base.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2020/10/17
 */
public class FriendCircleSolution1 {

    public static void main(String[] args) {
        int[][] friends = {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};
        int count = findCircleNum(friends);
        System.out.println(count);
    }

    public static int findCircleNum(int[][] friends) {
        if (friends == null || friends.length == 0) {
            return 0;
        }
        int n = friends.length;
        UF uf = new UF(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                if (friends[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.count;
    }

    static class UF {
        int[] id;
        int count;

        UF(int n) {
            id = new int[n];
            count = n;
            for (int i = 0; i < n; ++i) {
                id[i] = i;
            }
        }

        public void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) {
                return;
            }
            id[pRoot] = qRoot;
            --count;
        }

        public int find(int p) {
            while (p != id[p]) {
                p = id[p];
            }
            return p;
        }
    }
}

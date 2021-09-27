package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic200 {

    /**
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     *
     * <p>岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     *
     * <p>此外，你可以假设该网格的四条边均被水包围。
     */
    public static void main(String[] args) {
        char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println(numIslands1(grid));
    }

    public static int numIslands1(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int nr = grid.length, nc = grid[0].length;
        UnionFind union = new UnionFind(grid);
        for (int r = 0; r < nr; r++) {
            for (int c = 0; c < nc; c++) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0';
                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        union.union(r * nc + c, (r - 1) * nc + c);
                    }
                    if (r + 1 < nr && grid[r + 1][c] == '1') {
                        union.union(r * nc + c, (r + 1) * nc + c);
                    }
                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        union.union(r * nc + c, r * nc + c - 1);
                    }
                    if (c + 1 < nc && grid[r][c + 1] == '1') {
                        union.union(r * nc + c, r * nc + c + 1);
                    }
                }
            }
        }
        return union.getCount();
    }

    static class UnionFind {
        private int count;
        private int[] parent;
        private int[] rank;

        public UnionFind(char[][] grid) {
            count = 0;
            int r = grid.length, c = grid[0].length;
            parent = new int[r * c];
            rank = new int[r * c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    int dx = i * c + j;
                    if (grid[i][j] == '1') {
                        parent[dx] = dx;
                        count++;
                    }
                    rank[dx] = 0;
                }
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rx = find(x);
            int ry = find(y);
            if (rx != ry) {
                if (rank[rx] > rank[ry]) {
                    parent[ry] = rx;
                } else if (rank[rx] < rank[ry]) {
                    parent[rx] = ry;
                } else {
                    parent[ry] = rx;
                    rank[rx]++;
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }
    }
    // 根据根节点所在树的层级来判断合并方向
    // 层级矮的树往层级高的树合并不需要维护rank
    //        if (rank[pRoot] < rank[qRoot]) {
    //        parents[pRoot] = qRoot;
    //    } else if (rank[pRoot] > rank[qRoot]) {
    //        parents[qRoot] = pRoot;
    //    } else {//只有rank相等的情况才需要维护rank
    //        parents[pRoot] = qRoot;

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int nr = grid.length, nc = grid[0].length;
        int ans = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    ans += 1;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    public static void dfs(char[][] grid, int r, int c) {
        int nr = grid.length, nc = grid[0].length;
        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }
        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c + 1);
        dfs(grid, r, c - 1);
    }
}

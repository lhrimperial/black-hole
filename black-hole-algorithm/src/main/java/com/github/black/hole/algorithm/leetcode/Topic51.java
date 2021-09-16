package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/2/18
 */
public class Topic51 {
    /**
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * <p>给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     *
     * <p>每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     */
    public static void main(String[] args) {
        List<List<String>> resp1 = solveNQueue(4);
        resp1.forEach(
                item -> {
                    item.forEach(System.out::println);
                    System.out.println();
                });
    }

    public static List<List<String>> solveNQueue(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        int col = 0, pos = 0, neg = 0;
        boolean[][] chess = new boolean[n][n];
        List<List<String>> result = new ArrayList<>();
        dfsSolveNQueue(n, 0, col, pos, neg, chess, result);
        return result;
    }

    public static void dfsSolveNQueue(
            int n, int row, int col, int pos, int neg, boolean[][] chess, List<List<String>> resp) {
        if (n == row) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(chess[i][j] ? "Q" : "*").append("\t");
                }
                sub.add(sb.toString());
            }
            resp.add(sub);
        } else {
            // 取出列、右斜边、左斜边可选位置，截取n位有效位
            int spaces = ~(col | pos | neg) & ((1 << n) - 1);
            while (spaces > 0) {
                // 取最低位
                int lowBit = spaces & (-spaces);
                // 最低位所在bit位置
                int bitCount = Integer.bitCount(lowBit - 1);
                chess[row][bitCount] = true;
                // 通过设置当前行来影响下一行
                dfsSolveNQueue(
                        n,
                        row + 1,
                        col | lowBit,
                        (pos | lowBit) >> 1,
                        (neg | lowBit) << 1,
                        chess,
                        resp);
                chess[row][bitCount] = false;
                spaces &= spaces - 1;
            }
        }
    }

    public static List<List<String>> solveNQueues2(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        boolean[][] position = new boolean[n][n];
        Set<Integer> col = new HashSet<>();
        Set<Integer> pos = new HashSet<>();
        Set<Integer> neg = new HashSet<>();
        List<List<String>> resp = new ArrayList<>();
        solve(n, 0, col, pos, neg, position, resp);
        return resp;
    }

    public static void solve(
            int n,
            int row,
            Set<Integer> col,
            Set<Integer> pos,
            Set<Integer> neg,
            boolean[][] position,
            List<List<String>> resp) {
        if (n == row) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(position[i][j] ? "Q" : "*").append("\t");
                }
                sub.add(sb.toString());
            }
            resp.add(sub);
        } else {
            for (int i = 0; i < n; i++) {
                if (col.contains(i) || pos.contains(row + i) || neg.contains(row - i)) {
                    continue;
                }
                position[row][i] = true;
                col.add(i);
                pos.add(row + i);
                neg.add(row - i);
                solve(n, row + 1, col, pos, neg, position, resp);
                position[row][i] = false;
                col.remove(i);
                pos.remove(row + i);
                neg.remove(row - i);
            }
        }
    }

    /**
     * 回溯+剪枝
     *
     * @param n
     * @return
     */
    public static List<List<String>> solveNQueens1(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        boolean[][] chess = new boolean[n][n];
        List<List<String>> resp = new ArrayList<>();
        solve(n, 0, chess, resp);
        return resp;
    }

    public static void solve(int n, int row, boolean[][] chess, List<List<String>> resp) {
        if (n == row) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < chess.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < chess[i].length; j++) {
                    sb.append(chess[i][j] ? "Q" : "*").append("\t");
                }
                sub.add(sb.toString());
            }
            resp.add(sub);
        } else {
            for (int col = 0; col < n; col++) {
                if (isValid(n, row, col, chess)) {
                    chess[row][col] = true;
                    solve(n, row + 1, chess, resp);
                    chess[row][col] = false;
                }
            }
        }
    }

    public static boolean isValid(int n, int row, int col, boolean[][] chess) {
        // 列
        for (int i = row - 1; i >= 0; i--) {
            if (chess[i][col]) {
                return false;
            }
        }
        // 撇
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (chess[i][j]) {
                return false;
            }
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j]) {
                return false;
            }
        }
        return true;
    }
}

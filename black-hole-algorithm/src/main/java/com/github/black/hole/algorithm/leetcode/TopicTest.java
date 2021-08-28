package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2021/2/19
 */
public class TopicTest {

    public static void main(String[] args) {
        List<List<String>> resp = solveQueues(4);
        for (List<String> list : resp) {
            System.out.println(list.toString());
        }
    }

    public static List<List<String>> solveQueues(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        boolean[][] chess = new boolean[n][n];
        List<List<String>> result = new ArrayList<>();
        solve(0, n, chess, result);
        return result;
    }

    public static void solve(int row, int n, boolean[][] chess, List<List<String>> result) {
        if (row == n) {
            List<String> sub = new ArrayList<>();
            for (int i = 0; i < chess.length; i++) {
                StringBuilder bu = new StringBuilder();
                for (int j = 0; j < chess[i].length; j++) {
                    bu.append(chess[i][j] ? "Q" : "*");
                }
                sub.add(bu.toString());
            }
            result.add(sub);
        }
        for (int i = 0; i < n; i++) {
            if (isValid(row, i, n, chess)) {
                chess[row][i] = true;
                solve(row + 1, n, chess, result);
                chess[row][i] = false;
            }
        }
    }

    public static boolean isValid(int row, int col, int n, boolean[][] chess) {
        for (int i = row - 1; i >= 0; i--) {
            if (chess[i][col]) {
                return false;
            }
        }
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

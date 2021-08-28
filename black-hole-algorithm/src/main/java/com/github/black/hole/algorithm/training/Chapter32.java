package com.github.black.hole.algorithm.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter32 {
    /** 51 52 N皇后 */
    public static void main(String[] args) {
        List<List<String>> result = solveQueues(4);
        for (List<String> list : result) {
            System.out.println(list);
        }
    }

    public static List<List<String>> solveQueues(int n) {
        if (n == 0) {
            return Collections.emptyList();
        }
        char[][] chess = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                chess[i][j] = '.';
            }
        }
        List<List<String>> result = new ArrayList<>();
        solve(result, chess, 0);
        return result;
    }

    public static void solve(List<List<String>> res, char[][] chess, int row) {
        if (row == chess.length) {
            res.add(construct(chess));
            return;
        }
        for (int i = 0; i < chess.length; i++) {
            if (isValid(chess, row, i)) {
                chess[row][i] = 'Q';
                solve(res, chess, row + 1);
                chess[row][i] = '.';
            }
        }
    }

    public static boolean isValid(char[][] chess, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (chess[i][col] == 'Q') {
                return false;
            }
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < chess[row].length; i--, j++) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (chess[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static List<String> construct(char[][] chars) {
        List<String> path = new ArrayList<>();
        for (int i = 0, len = chars.length; i < len; i++) {
            path.add(new String(chars[i]));
        }
        return path;
    }
}

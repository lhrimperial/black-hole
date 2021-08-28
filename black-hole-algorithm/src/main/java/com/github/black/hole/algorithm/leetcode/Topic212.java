package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class Topic212 {

    public static void main(String[] args) {
        char[][] board = {
            {'o', 'a', 'a', 'n'}, {'e', 't', 'a', 'e'}, {'i', 'h', 'k', 'r'}, {'i', 'f', 'l', 'v'}
        };
        String[] words = {"oath", "oaa", "eat", "rain"};
        List<String> res = findWords(board, words);
        System.out.println(res.toString());
    }

    public static List<String> findWords(char[][] board, String[] words) {
        if (board == null || board.length == 0 || words == null || words.length == 0) {
            return Collections.emptyList();
        }
        Trie trie = new Trie();
        for (String str : words) {
            trie.insert(str);
        }
        int row = board.length, col = board[0].length;
        boolean[][] visited = new boolean[row][col];
        List<String> res = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dfs(board, "", i, j, visited, trie, res);
            }
        }
        return res;
    }

    public static void dfs(
            char[][] board,
            String str,
            int row,
            int col,
            boolean[][] visited,
            Trie trie,
            List<String> res) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }
        if (visited[row][col]) {
            return;
        }
        str += board[row][col];
        if (!trie.startWith(str)) {
            return;
        }
        if (trie.search(str)) {
            res.add(str);
        }
        visited[row][col] = true;
        dfs(board, str, row + 1, col, visited, trie, res);
        dfs(board, str, row - 1, col, visited, trie, res);
        dfs(board, str, row, col + 1, visited, trie, res);
        dfs(board, str, row, col - 1, visited, trie, res);
        visited[row][col] = false;
    }
}

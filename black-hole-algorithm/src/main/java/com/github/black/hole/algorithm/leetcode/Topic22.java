package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/2/18
 */
public class Topic22 {
    /** 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 */
    public static void main(String[] args) {
        System.out.println(generateParenthesis1(3).toString());
    }

    public static List<String> generateParenthesis(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        dfs("", n, n, result);
        return result;
    }

    /**
     * 深度优先
     *
     * @param currStr
     * @param left
     * @param right
     * @param res
     */
    public static void dfs(String currStr, int left, int right, List<String> res) {
        if (left == 0 && right == 0) {
            res.add(currStr);
            return;
        }
        if (left > 0) {
            dfs(currStr + "(", left - 1, right, res);
        }
        if (right > left) {
            dfs(currStr + ")", left, right - 1, res);
        }
    }

    public static List<String> generateParenthesis1(int n) {
        if (n < 1){
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node("", n, n));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left == 0 && node.right == 0){
                result.add(node.value);
            }
            if (node.left > 0){
                queue.offer(new Node(node.value + "(", node.left - 1, node.right));
            }
            if (node.right > node.left){
                queue.offer(new Node(node.value + ")", node.left, node.right - 1));
            }
        }

        return result;
    }


    private static class Node {
        private String value;
        private int left;
        private int right;

        public Node(String value, int left, int right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }
    }
}

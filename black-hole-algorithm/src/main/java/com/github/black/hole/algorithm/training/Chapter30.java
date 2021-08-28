package com.github.black.hole.algorithm.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter30 {

    /** 22 生成有效括号 */
    public static void main(String[] args) {
        List<String> result = dfsGen(3);
        System.out.println("dfs gen:" + result.toString());
        System.out.println("bfs gen:" + bfsGen(3));
    }

    public static List<String> bfsGen(int n) {
        if (n < 0) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        Deque<Node> queue = new LinkedList<>();
        queue.offer(new Node("", n, n));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left == 0 && node.right == 0) {
                result.add(node.value);
            }
            if (node.left > 0) {
                queue.offer(new Node(node.value + "(", node.left - 1, node.right));
            }
            if (node.right > 0 && node.right > node.left) {
                queue.offer(new Node(node.value + ")", node.left, node.right - 1));
            }
        }
        return result;
    }

    public static List<String> dfsGen(int n) {
        if (n < 1) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        dfsGen("", result, n, n);
        return result;
    }

    public static void dfsGen(String curr, List<String> result, int left, int right) {
        if (left == 0 && right == 0) {
            result.add(curr);
            return;
        }
        if (left > 0) {
            dfsGen(curr + "(", result, left - 1, right);
        }
        if (right > 0 && left < right) {
            dfsGen(curr + ")", result, left, right - 1);
        }
    }

    private static class Node {
        public String value;
        public int left;
        public int right;

        public Node(String value, int left, int right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}

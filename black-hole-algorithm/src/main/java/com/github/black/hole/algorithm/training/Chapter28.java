package com.github.black.hole.algorithm.training;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter28 {

    /** 102 层次遍历 */
    public static void main(String[] args) {
        int[] arr = {8, 6, 10, 4, 7, 9, 11, 3, 5};
        TreeNode node = TreeNode.buildTree(arr);
        System.out.println("bfs:" + bfsLevelOrder(node));
        System.out.println("bfs:" + dfsLevelOrder(node));
    }

    public static List<List<Integer>> dfsLevelOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        dfsLevelOrder(1, node, result);
        return result;
    }

    public static void dfsLevelOrder(int level, TreeNode node, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        if (result.size() < level) {
            result.add(new ArrayList<>());
        }
        result.get(level - 1).add(node.val);
        if (node.left != null) {
            dfsLevelOrder(level + 1, node.left, result);
        }
        if (node.right != null) {
            dfsLevelOrder(level + 1, node.right, result);
        }
    }

    public static List<List<Integer>> bfsLevelOrder(TreeNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> sub = new ArrayList<>(levelSize);
            for (int i = 0; i < levelSize; i++) {
                node = queue.poll();
                sub.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(sub);
        }
        return result;
    }
}

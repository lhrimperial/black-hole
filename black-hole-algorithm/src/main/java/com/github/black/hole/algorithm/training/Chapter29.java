package com.github.black.hole.algorithm.training;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter29 {

    /** 104 ,111 最小最大深度 */
    public static void main(String[] args) {
        int[] arr = {8, 6, 10, 4, 7, 9, 11, 3, 5};
        TreeNode node = TreeNode.buildTree(arr);
        List<List<Integer>> result = new ArrayList<>();
        TreeNode.leveOrder(node, result);
        System.out.println(result.toString());
        System.out.println("max depth:" + dfsMaxDepth(node));
        System.out.println("max depth:" + bfsMaxDepth(node));
        System.out.println("min depth:" + dfsMinDepth(node));
        System.out.println("min depth:" + bfsMinDepth(node));
    }

    public static int bfsMinDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int min = 1;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            while (levelSize > 0) {
                node = queue.poll();
                if (node.left == null && node.left == null) {
                    return min;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                levelSize--;
            }
            min++;
        }
        return min;
    }

    public static int dfsMinDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfsMinDepth(node.left);
        int right = dfsMinDepth(node.right);
        return (left == 0 || right == 0) ? 1 + left + right : 1 + Math.min(left, right);
    }

    public static int bfsMaxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int max = 0;
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            while (levelSize > 0) {
                node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                levelSize--;
            }
            max++;
        }
        return max;
    }

    /**
     * dfs
     *
     * @param node
     * @return
     */
    public static int dfsMaxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(dfsMaxDepth(node.left), dfsMaxDepth(node.right));
    }
}

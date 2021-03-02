package com.github.black.hole.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/2/18
 */
public class Topic111 {
    /**
     * 给定一个二叉树，找出其最小深度。
     *
     * <p>最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * <p>说明：叶子节点是指没有子节点的节点。
     */
    public static void main(String[] args) {
        int[] arr = {3, 2, 4, 7, 8, 6, 5, 9};
        TreeNode node = TreeNode.buildTree(arr);
        System.out.println("min depth: " + minDepth1(node));
    }

    public static int minDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int min = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            while (levelSize > 0) {
                TreeNode node = queue.poll();
                if (node.getLeft() == null && node.getRight() == null) {
                    return min;
                }
                if (node.getLeft() != null) {
                    queue.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.offer(node.getRight());
                }
                levelSize--;
            }
            min++;
        }
        return min;
    }

    /**
     * dfs
     *
     * @param root
     * @return
     */
    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = minDepth(root.getLeft());
        int right = minDepth(root.getRight());
        return (left == 0 || right == 0) ? left + right + 1 : 1 + Math.min(left, right);
    }
}

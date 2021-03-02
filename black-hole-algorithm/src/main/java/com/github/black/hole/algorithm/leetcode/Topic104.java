package com.github.black.hole.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/2/18
 */
public class Topic104 {
    /**
     * 给定一个二叉树，找出其最大深度。
     *
     * <p>二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * <p>说明: 叶子节点是指没有子节点的节点。
     */
    public static void main(String[] args) {
        int[] arr = {3, 2, 4, 7, 8, 6, 5, 9};
        TreeNode node = TreeNode.buildTree(arr);
        System.out.println("max depth: " + maxDepth1(node));
    }

    /**
     * bfs
     *
     * @param root
     * @return
     */
    public static int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int ans = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            while (levelSize > 0) {
                TreeNode node = queue.poll();
                if (node.getLeft() != null) {
                    queue.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.offer(node.getRight());
                }
                levelSize--;
            }
            ans++;
        }
        return ans;
    }

    /**
     * dfs
     *
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.getLeft()), maxDepth(root.getRight()));
    }
}

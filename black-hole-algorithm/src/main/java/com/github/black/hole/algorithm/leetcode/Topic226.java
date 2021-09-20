package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * @author hairen.long
 * @date 2021/9/18
 */
public class Topic226 {

    /** 226. 翻转二叉树 */
    public static void main(String[] args) {
        int[] arr = {8, 5, 6, 3, 4, 2, 10, 9, 11};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> result = new ArrayList<>();
        TreeNode.preOrder(node, result);
        System.out.println(result.toString());
        invertTree(node);
        result.clear();
        TreeNode.preOrder(node, result);
        System.out.println(result.toString());

        invertTree1(node);
        result.clear();
        TreeNode.preOrder(node, result);
        System.out.println(result.toString());
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        root.left = invertTree(root.right);
        root.right = left;
        return root;
    }

    public static TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) {
                queue.offer(node.getLeft());
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
}

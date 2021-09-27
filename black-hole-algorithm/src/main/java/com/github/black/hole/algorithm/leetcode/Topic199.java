package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/24
 */
public class Topic199 {

    /**
     * 199. 二叉树的右视图
     *
     * <p>给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     */
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(5);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(4);
        List<Integer> res = rightSideView(root);
        System.out.println(res.toString());
        res.clear();
        res = rightSideView1(root);
        System.out.println(res.toString());
    }

    public static List<Integer> rightSideView1(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        dfs(root, 0, res);
        return res;
    }

    public static void dfs(TreeNode root, int depth, List<Integer> res) {
        if (root == null) {
            return;
        }
        if (depth == res.size()) {
            res.add(root.value);
        }
        depth++;
        dfs(root.right, depth, res);
        dfs(root.left, depth, res);
    }

    public static List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int ls = queue.size();
            for (int i = 0; i < ls; i++) {
                TreeNode node = queue.poll();
                if (i == ls - 1) {
                    result.add(node.value);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return result;
    }
}

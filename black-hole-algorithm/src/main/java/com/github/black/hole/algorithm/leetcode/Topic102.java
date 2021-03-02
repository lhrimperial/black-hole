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
public class Topic102 {

    /** 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。 */
    public static void main(String[] args) {
        int[] arr = {6, 4, 9, 3, 5, 8, 10};
        TreeNode node = TreeNode.buildTree(arr);
        List<List<Integer>> result = dfsLevelOrder(node);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> dfsLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        dfsLevelOrder(1, root, result);
        return result;
    }

    public static void dfsLevelOrder(int level, TreeNode node, List<List<Integer>> res) {
        if (res.size() < level) {
            res.add(new ArrayList<>());
        }
        res.get(level - 1).add(node.getValue());
        if (node.getLeft() != null) {
            dfsLevelOrder(level + 1, node.getLeft(), res);
        }
        if (node.getRight() != null) {
            dfsLevelOrder(level + 1, node.getRight(), res);
        }
    }

    /**
     * 广度优先搜索
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> bfsLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> levelResult = new ArrayList<>();
            for (int i = 0, len = queue.size(); i < len; i++) {
                TreeNode node = queue.poll();
                levelResult.add(node.getValue());
                if (node.getLeft() != null) {
                    queue.offer(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.offer(node.getRight());
                }
            }
            result.add(levelResult);
        }

        return result;
    }
}

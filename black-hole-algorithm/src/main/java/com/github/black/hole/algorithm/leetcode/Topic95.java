package com.github.black.hole.algorithm.leetcode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/10/2
 */
public class Topic95 {
    /** 95. 不同的二叉搜索树 II 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。 */
    public static void main(String[] args) {
        List<TreeNode> nodes = generateTrees(3);
        nodes.forEach(
                node -> {
                    List<Integer> res = new LinkedList<>();
                    TreeNode.preOrder(node, res);
                    System.out.println(res.toString());
                });
    }

    public static List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        return getAnts(1, n);
    }

    public static List<TreeNode> getAnts(int start, int end) {
        List<TreeNode> res = new LinkedList<>();
        if (start > end) {
            res.add(null);
            return res;
        }
        if (start == end) {
            TreeNode node = new TreeNode(start);
            res.add(node);
            return res;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> lefts = getAnts(start, i - 1);
            List<TreeNode> rights = getAnts(i + 1, end);
            for (TreeNode left : lefts) {
                for (TreeNode right : rights) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }
}

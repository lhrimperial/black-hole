package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/2/17
 */
public class Topic235 {

    /**
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     *
     * <p>输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     *
     * <p>输出: 6 解释: 节点 2 和节点 8 的最近公共祖先是 6。
     */
    public static void main(String[] args) {
        int[] arr = {2, 3};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> levelOrder = new ArrayList<>();
        TreeNode.levelOrder(node, levelOrder);
        System.out.println("层次遍历:" + levelOrder);
        TreeNode result = lowestCommonAncestor(node, new TreeNode(3), new TreeNode(2));
        System.out.println(result.getValue());
    }

    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        while (root != null) {
            if (p.getValue() < root.getValue() && q.getValue() < root.getValue()) {
                root = root.getLeft();
            } else if (p.getValue() > root.getValue() && q.getValue() > root.getValue()) {
                root = root.getRight();
            } else {
                return root;
            }
        }
        return null;
    }

    public static TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        if (p.getValue() < root.getValue() && q.getValue() < root.getValue()) {
            return lowestCommonAncestor(root.getLeft(), p, q);
        } else if (p.getValue() > root.getValue() && q.getValue() > root.getValue()) {
            return lowestCommonAncestor(root.getRight(), p, q);
        }
        return root;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || Objects.equals(root, p) || Objects.equals(root, q)) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.getLeft(), p, q);
        TreeNode right = lowestCommonAncestor(root.getRight(), p, q);
        return left == null ? right : right == null ? left : root;
    }
}

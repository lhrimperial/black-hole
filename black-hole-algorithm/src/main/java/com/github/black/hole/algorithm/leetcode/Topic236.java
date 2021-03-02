package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/2/17
 */
public class Topic236 {

    /**
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     *
     * <p>输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     *
     * <p>输出：3
     *
     * <p>解释：节点5 和节点 1 的最近公共祖先是节点 3 。
     */
    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 6, 2, 0, 8, 7, 4};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> levelOrder = new ArrayList<>();
        TreeNode.levelOrder(node, levelOrder);
        System.out.println("层次遍历：" + levelOrder.toString());
        TreeNode result = lowestCommonAncestor(node, new TreeNode(4), new TreeNode(6));
        System.out.println(result.getValue());
    }

    /**
     * 使用递归查找
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || Objects.equals(root, p) || Objects.equals(root, q)) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.getLeft(), p, q);
        TreeNode right = lowestCommonAncestor(root.getRight(), p, q);
        return left == null ? right : right == null ? left : root;
    }
}

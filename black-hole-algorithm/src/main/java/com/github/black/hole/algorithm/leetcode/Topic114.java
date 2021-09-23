package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic114 {

    /** 114. 二叉树展开为链表 */
    public static void main(String[] args) {
        int[] arr = {8, 5, 3, 6, 10, 9, 11};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> res = new ArrayList<>();
        TreeNode.preOrder(node, res);
        System.out.println(res.toString());
        flatten(node);
        res.clear();
        TreeNode.preOrder(node, res);
        System.out.println(res.toString());
    }

    public static void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = null;
        root.right = left;

        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }
}

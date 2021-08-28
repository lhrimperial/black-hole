package com.github.black.hole.algorithm.training;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2021/8/22
 */
public class Chapter19 {

    /** 二叉搜索树公共祖先 */
    public static void main(String[] args) {
        int[] arr = {8, 6, 10, 4, 7, 9, 11, 3, 5};
        TreeNode node = TreeNode.buildTree(arr);
        TreeNode result = lowestCommonAncestor(node, new TreeNode(4), new TreeNode(7));
        System.out.println(result.val);

        TreeNode result1 = lowestCommonAncestor1(node, new TreeNode(4), new TreeNode(7));
        System.out.println(result1.val);

        TreeNode result2 = lowestCommonAncestor2(node, new TreeNode(4), new TreeNode(7));
        System.out.println(result2.val);
    }

    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        while (root != null) {
            if (p.val <= root.val && q.val <= root.val) {
                root = root.left;
            } else if (p.val >= root.val && q.val >= root.val) {
                root = root.right;
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
        if (p.val <= root.val && q.val <= root.val) {
            return lowestCommonAncestor1(root.left, p, q);
        } else if (p.val >= root.val && q.val >= root.val) {
            return lowestCommonAncestor1(root.right, p, q);
        }
        return root;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || Objects.equals(root, p) || Objects.equals(root, q)) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        return left == null ? right : right == null ? left : root;
    }
}

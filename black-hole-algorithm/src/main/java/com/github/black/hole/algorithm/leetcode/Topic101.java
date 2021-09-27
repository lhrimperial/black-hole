package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/9/25
 */
public class Topic101 {

    /** 101. 对称二叉树 */
    public static void main(String[] args) {

    }

    public static boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public static boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.value == q.value && check(p.left, q.right) && check(p.right, q.left);
    }
}

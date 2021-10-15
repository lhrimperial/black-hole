package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/21
 */
public class Topic99 {

    /** 99. 恢复二叉搜索树 */
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(1);
        root.left.right = new TreeNode(2);
        recoverTree1(root);

        List<Integer> result = new ArrayList<>();
        TreeNode.inOrder(root, result);
        System.out.println(result.toString());
    }

    static TreeNode prev, node1, node2;
    public static void recoverTree1(TreeNode root) {
        if (root == null) {
            return;
        }
        traver(root);
        int t = node1.value;
        node1.value = node2.value;
        node2.value = t;
    }

    private static void traver(TreeNode root) {
        if (root == null) {
            return;
        }
        traver(root.left);
        if (prev != null && prev.value > root.value) {
            if (node1 == null) {
                node1 = prev;
            }
            node2 = root;
        }

        prev = root;
        traver(root.right);
    }

    private static TreeNode first;
    private static TreeNode second;
    private static TreeNode pre = new TreeNode(Integer.MIN_VALUE);

    public static void recoverTree(TreeNode root) {
        inorder(root);
        int tmp = first.value;
        first.value = second.value;
        second.value = tmp;
    }

    /**
     * 通过中序遍历找到根节点比右子树小的作为第一个节点，找到右子树小于根节点的作为第二个节点
     *
     * @param root
     */
    private static void inorder(TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        if (first == null && pre.value > root.value) {
            first = pre;
        }
        if (first != null && pre.value > root.value) {
            second = root;
        }
        pre = root;
        inorder(root.right);
    }
}

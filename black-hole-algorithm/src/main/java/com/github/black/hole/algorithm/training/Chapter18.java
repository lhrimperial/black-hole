package com.github.black.hole.algorithm.training;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/8/21
 */
public class Chapter18 {
    /** 98 验证二叉搜索树 */
    public static void main(String[] args) {
        int[] arr = {8, 6, 10, 4, 7, 9, 11, 3, 5};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> preResult1 = new ArrayList<>();
        TreeNode.preOrder(node, preResult1);
        System.out.println("递归前序遍历：" + preResult1.toString());
        List<Integer> preResult2 = new ArrayList<>();
        TreeNode.preOrderNoRecursive(node, preResult2);
        System.out.println("非递归前序遍历：" + preResult2.toString());

        List<Integer> inResult1 = new ArrayList<>();
        TreeNode.inOrder(node, inResult1);
        System.out.println("递归中序遍历：" + inResult1.toString());
        List<Integer> inResult2 = new ArrayList<>();
        TreeNode.inOrderNoRecursive(node, inResult2);
        System.out.println("非递归中序遍历：" + inResult2.toString());

        List<Integer> postResult1 = new ArrayList<>();
        TreeNode.postOrder(node, postResult1);
        System.out.println("递归后序遍历：" + postResult1.toString());
        List<Integer> postResult2 = new ArrayList<>();
        TreeNode.postOrderNoRecursive(node, postResult2);
        System.out.println("非递归后序遍历：" + postResult2.toString());

        List<List<Integer>> levelOrder = new ArrayList<>();
        TreeNode.leveOrder(node, levelOrder);
        System.out.println("层次遍历：" + levelOrder.toString());

        System.out.println("是否是二叉搜索树：" + isValidBST1(TreeNode.buildTree(arr)));
        System.out.println("是否是二叉搜索树：" + isValidBST2(TreeNode.buildTree(arr)));
    }

    private static TreeNode prev;

    public static boolean isValidBST2(TreeNode node) {
        if (node == null) {
            return true;
        }
        if (isValidBST2(node.left)) {
            return true;
        }
        if (prev != null && prev.val > node.val) {
            return false;
        }
        prev = node;
        if (isValidBST2(node.right)) {
            return true;
        }
        return false;
    }

    public static boolean isValidBST1(TreeNode node) {
        if (node == null) {
            return true;
        }
        return isValidBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isValidBST(TreeNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
    }
}

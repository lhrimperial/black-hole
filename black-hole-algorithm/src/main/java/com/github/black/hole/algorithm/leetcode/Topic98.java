package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/2/15
 */
public class Topic98 {

    /**
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     *
     * <p>假设一个二叉搜索树具有如下特征：
     *
     * <p>节点的左子树只包含小于当前节点的数。
     *
     * <p>节点的右子树只包含大于当前节点的数。
     *
     * <p>所有左子树和右子树自身必须也是二叉搜索树。
     */
    public static void main(String[] args) {
        int[] arr = {6, 4, 3, 5, 9, 8, 10};
        TreeNode node = TreeNode.buildTree(arr);
        List<Integer> preOrder = new ArrayList<>();
        TreeNode.preOrder(node, preOrder);
        System.out.println("前序遍历：" + preOrder.toString());

        List<Integer> preOrderNon = new ArrayList<>();
        TreeNode.preOrderNoRecursive(node, preOrderNon);
        System.out.println("前序遍历非递归：" + preOrderNon.toString());

        List<Integer> inOrder = new ArrayList<>();
        TreeNode.inOrder(node, inOrder);
        System.out.println("中序遍历：" + inOrder.toString());

        List<Integer> inOrderNon = new ArrayList<>();
        TreeNode.inOrderNonRecursive(node, inOrderNon);
        System.out.println("中序遍历非递归：" + inOrderNon.toString());

        List<Integer> postOrder = new ArrayList<>();
        TreeNode.postOrder(node, postOrder);
        System.out.println("后序遍历：" + postOrder.toString());

        List<Integer> postOrderNon = new ArrayList<>();
        TreeNode.postOrderNonRecursive(node, postOrderNon);
        System.out.println("后序遍历非递归：" + postOrderNon.toString());

        List<Integer> levelOrder = new ArrayList<>();
        TreeNode.levelOrder(node, levelOrder);
        System.out.println("层次遍历：" + levelOrder.toString());

        System.out.println("是否是二叉搜索树：" + isValidBST1(node));
    }

    /** 使用中序遍历加前驱节点 */
    private static TreeNode prev;

    public static boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!isValidBST1(root.getLeft())) {
            return false;
        }
        if (prev != null && prev.getValue() > root.getValue()) {
            return false;
        }
        prev = root;
        if (!isValidBST1(root.getRight())) {
            return false;
        }
        return true;
    }

    /**
     * 使用递归和
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode node, int lower, int upper) {
        if (node == null) {
            return true;
        }
        if (node.getValue() < lower || node.getValue() > upper) {
            return false;
        }
        return isValidBST(node.getLeft(), lower, node.getValue())
                && isValidBST(node.getRight(), node.getValue(), upper);
    }
}

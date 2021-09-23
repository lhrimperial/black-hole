package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/18
 */
public class Topic105 {

    /**
     * 给定一棵树的前序遍历 preorder 与中序遍历 inorder。请构造二叉树并返回其根节点。
     *
     * <p>Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7] Output: [3,9,20,null,null,15,7]
     */
    public static void main(String[] args) {
        TreeNode node = buildTree(new int[] {3, 9, 20, 15, 7}, new int[] {9, 3, 15, 20, 7});
        List<Integer> result = new ArrayList<>();
        TreeNode.preOrder(node, result);
        System.out.println(result.toString());
    }

    /**
     * 前序遍历第一个总是根，中序遍历的根前面的都是左子树，后半部分都是右子树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) {
            return null;
        }
        Map<Integer, Integer> inorderMap = new HashMap<>(inorder.length);
        for (int i = 0, len = inorder.length; i < len; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTree(
                preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderMap);
    }

    public static TreeNode buildTree(
            int[] preorder,
            int preStart,
            int preEnd,
            int[] inorder,
            int inStart,
            int inEnd,
            Map<Integer, Integer> inMap) {

        if (preStart > preEnd || inStart > inEnd) return null;

        TreeNode root = new TreeNode(preorder[preStart]);
        int inRoot = inMap.get(root.value);
        int numsLeft = inRoot - inStart;

        root.left =
                buildTree(
                        preorder,
                        preStart + 1,
                        preStart + numsLeft,
                        inorder,
                        inStart,
                        inRoot - 1,
                        inMap);
        root.right =
                buildTree(
                        preorder,
                        preStart + numsLeft + 1,
                        preEnd,
                        inorder,
                        inRoot + 1,
                        inEnd,
                        inMap);
        return root;
    }
}

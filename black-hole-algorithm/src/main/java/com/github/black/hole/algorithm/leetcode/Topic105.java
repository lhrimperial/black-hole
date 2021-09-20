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

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length, inorder, 0, inorder.length, map);
    }

    private static TreeNode buildTreeHelper(
            int[] preorder,
            int p_start,
            int p_end,
            int[] inorder,
            int i_start,
            int i_end,
            HashMap<Integer, Integer> map) {
        if (p_start == p_end) {
            return null;
        }
        int root_val = preorder[p_start];
        TreeNode root = new TreeNode(root_val);
        int i_root_index = map.get(root_val);
        int leftNum = i_root_index - i_start;
        root.left =
                buildTreeHelper(
                        preorder,
                        p_start + 1,
                        p_start + leftNum + 1,
                        inorder,
                        i_start,
                        i_root_index,
                        map);
        root.right =
                buildTreeHelper(
                        preorder,
                        p_start + leftNum + 1,
                        p_end,
                        inorder,
                        i_root_index + 1,
                        i_end,
                        map);
        return root;
    }
}

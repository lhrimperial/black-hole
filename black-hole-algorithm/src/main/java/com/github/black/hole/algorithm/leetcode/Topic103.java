package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic103 {
    /** 103. 二叉树的锯齿形层序遍历 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。 */
    public static void main(String[] args) {
        int[] arr = {8, 6, 5, 7, 4, 10, 9, 11, 10};
        TreeNode node = TreeNode.buildTree(arr);
        List<List<Integer>> result = zigzagLevelOrder(node);
        System.out.println(result.toString());
    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean order = true;
        while (!queue.isEmpty()) {
            int ls = queue.size();
            Deque<Integer> sub = new ArrayDeque<>();
            while (ls > 0) {
                TreeNode node = queue.poll();
                if (order) {
                    sub.addFirst(node.value);
                } else {
                    sub.addLast(node.value);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                ls--;
            }
            result.add(new ArrayList<>(sub));
            order = !order;
        }
        return result;
    }
}

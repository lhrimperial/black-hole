package com.github.black.hole.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2021/9/26
 */
public class Topic653 {

    /** 653. 两数之和 IV - 输入 BST */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode node = TreeNode.buildTree(arr);
        System.out.println(findTarget(node, 10));
    }

    public static boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        return find(root, k, set);
    }

    public static boolean find(TreeNode root, int k, Set<Integer> sets) {
        if (root == null) {
            return false;
        }
        if (sets.contains(k - root.value)) {
            return true;
        }
        sets.add(root.value);
        return find(root.left, k, sets) || find(root.right, k, sets);
    }
}

package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic654 {
    /**
     * 654. 最大二叉树 给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
     *
     * <p>二叉树的根是数组 nums 中的最大元素。 左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。 右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。
     * 返回有给定数组 nums 构建的 最大二叉树 。
     */
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 6, 0, 5};
        TreeNode node = constructMaximumBinaryTree(arr);
        List<Integer> res = new ArrayList<>();
        TreeNode.preOrder(node, res);
        System.out.println(res.toString());
    }

    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return build(nums, 0, nums.length - 1);
    }

    public static TreeNode build(int[] nums, int low, int high) {
        if (low > high) {
            return null;
        }
        int index = -1, max = Integer.MIN_VALUE;
        for (int i = low; i <= high; i++) {
            if (nums[i] > max) {
                max = nums[i];
                index = i;
            }
        }
        TreeNode root = new TreeNode(max);
        root.left = build(nums, low, index - 1);
        root.right = build(nums, index + 1, high);
        return root;
    }
}

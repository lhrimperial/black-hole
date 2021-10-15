package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/18
 */
public class Topic124 {

    /**
     * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     *
     * <p>路径和 是路径中各节点值的总和。
     *
     * <p>给你一个二叉树的根节点 root ，返回其 最大路径和 。
     *
     * <p>输入：root = [1,2,3] 输出：6 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
     */
    public static void main(String[] args) {
        TreeNode node = TreeNode.buildTree(new int[] {1, 2, 3});
        maxPathSum(node);
        System.out.println("ans = " + ans);
    }

    private static int ans = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = Math.max(maxPathSum(root.left), 0);
        int right = Math.max(maxPathSum(root.getRight()), 0);
        ans = Math.max(ans, left + right + root.getValue());
        return root.getValue() + Math.max(left, right);
    }
}

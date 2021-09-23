package com.github.black.hole.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/22
 */
public class Topic337 {

    /**
     * 337. 打家劫舍 III
     *
     * <p>在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。
     * 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
     * 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。
     *
     * <p>计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
     */
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(1);
        System.out.println(rob(root));
        System.out.println(robInternal(root, new HashMap<>()));

        int[] result = robInternal1(root);
        System.out.println(Math.max(result[0], result[1]));
    }

    public static int[] robInternal1(TreeNode root) {
        if (root == null) {
            return new int[2];
        }
        int[] result = new int[2];

        int[] left = robInternal1(root.left);
        int[] right = robInternal1(root.right);

        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        result[1] = left[0] + right[0] + root.value;
        return result;
    }

    public static int robInternal(TreeNode root, Map<TreeNode, Integer> memo) {
        if (root == null) {
            return 0;
        }
        if (memo.containsKey(root)) {
            return memo.get(root);
        }
        int money = root.value;
        if (root.left != null) {
            money += (robInternal(root.left.left, memo) + robInternal(root.left.right, memo));
        }
        if (root.right != null) {
            money += (robInternal(root.right.left, memo) + robInternal(root.right.right, memo));
        }
        int result =
                Math.max(money, (robInternal(root.left, memo) + robInternal(root.right, memo)));
        memo.put(root, result);
        return result;
    }

    public static int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int money = root.value;
        if (root.left != null) {
            money += (rob(root.left.left) + rob(root.left.right));
        }
        if (root.right != null) {
            money += (rob(root.right.left) + rob(root.right.right));
        }
        return Math.max(money, (rob(root.left) + rob(root.right)));
    }
}

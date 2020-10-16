package com.github.black.hole.base.algorithm.tree;

import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/10/16
 */
@Data
public class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}

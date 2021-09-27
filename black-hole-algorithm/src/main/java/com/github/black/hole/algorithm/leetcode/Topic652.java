package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/9/23
 */
public class Topic652 {

    /** 652. 寻找重复的子树 */
    public static void main(String[] args) {

    }

    public static List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        Map<String, Integer> counter = new HashMap<>();
        List<TreeNode> ans = new ArrayList<>();
        count(root, ans, counter);
        return ans;
    }

    public static String count(TreeNode root, List<TreeNode> ans, Map<String, Integer> counter) {
        if (root == null) {
            return "#";
        }
        String key = root.value + count(root.left,ans,counter) + count(root.right, ans, counter);
        counter.put(key,counter.getOrDefault(key,0)+1);
        if (counter.get(key) == 2) {
            ans.add(root);
        }
        return key;
    }
}

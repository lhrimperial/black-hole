package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/9/8
 */
public class Topic1408 {

    /**
     * 给你一个字符串数组 words ，数组中的每个字符串都可以看作是一个单词。请你按 任意 顺序返回 words 中是其他单词的子字符串的所有单词。
     *
     * <p>如果你可以删除 words[j] 最左侧和/或最右侧的若干字符得到 word[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。
     */
    public static void main(String[] args) {}

    public static List<String> stringMatching(String[] words) {
        List<String> list = new ArrayList<>();
        if (words.length == 0) {
            return list;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            builder.append(str).append(",");
        }

        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            if (builder.toString().indexOf(str) != builder.toString().lastIndexOf(str)) {
                list.add(str);
            }
        }
        return list;
    }
}

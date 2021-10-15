package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/10/11
 */
public class Topic58 {

    /** 58. 最后一个单词的长度 */
    public static void main(String[] args) {
        String s = "   fly me   to   the moon  ";
        System.out.println(lengthOfLastWord(s));
    }

    public static int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int index = s.length() - 1;
        while (s.charAt(index) == ' ') {
            index--;
        }
        int word = 0;
        while (index >= 0 && s.charAt(index) != ' ') {
            word++;
            index--;
        }
        return word;
    }
}

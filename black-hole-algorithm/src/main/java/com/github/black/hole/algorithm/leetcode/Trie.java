package com.github.black.hole.algorithm.leetcode;

/**
 * @author hairen.long
 * @date 2021/8/25
 */
public class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode(' ');
    }

    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        int index;
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode(ch);
            }
            node = node.children[index];
        }
        node.isWord = true;
    }

    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        int index;
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isWord;
    }

    public boolean startWith(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return false;
        }
        int index;
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return true;
    }
}

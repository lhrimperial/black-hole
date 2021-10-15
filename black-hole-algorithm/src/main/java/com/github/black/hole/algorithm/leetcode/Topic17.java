package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/10/10
 */
public class Topic17 {
    /** 17. 电话号码的字母组合 */
    public static void main(String[] args) {
        List<String> res = letterCombinations("23");
        System.out.println(res.toString());
    }

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return Collections.emptyList();
        }
        Map<Character, String> phoneMap =
                new HashMap<Character, String>() {
                    {
                        put('2', "abc");
                        put('3', "def");
                        put('4', "ghi");
                        put('5', "jkl");
                        put('6', "mno");
                        put('7', "pqrs");
                        put('8', "tuv");
                        put('9', "wxyz");
                    }
                };
        List<String> resp = new ArrayList<>();
        dfs(digits, 0, resp, new StringBuilder(), phoneMap);
        return resp;
    }

    public static void dfs(String digits, int index, List<String> resp, StringBuilder path, Map<Character, String> phoneMap) {
        if (index == digits.length()) {
            resp.add(path.toString());
            return;
        }
        String str = phoneMap.get(digits.charAt(index));
        for (int i = 0; i < str.length(); i++) {
            path.append(str.charAt(i));
            dfs(digits, index + 1, resp, path, phoneMap);
            path.deleteCharAt(index);
        }

    }

}

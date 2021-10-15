package com.github.black.hole.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/10/4
 */
public class Topic6 {
    /** 6. Z 字形变换 */
    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        int numRows = 3;
        //PAHNAPLSIIGYIR
        System.out.println(convert(s, numRows));
    }

    public static String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        List<StringBuilder> lines = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            lines.add(new StringBuilder());
        }
        int i = 0, flag = -1;
        for (char ch : s.toCharArray()) {
            lines.get(i).append(ch);
            if (i == 0 || i == numRows - 1) {
                flag = -flag;
            }
            i += flag;
        }
        StringBuilder sb = new StringBuilder();
        for (StringBuilder builder : lines) {
            sb.append(builder.toString());
        }
        return sb.toString();
    }
}

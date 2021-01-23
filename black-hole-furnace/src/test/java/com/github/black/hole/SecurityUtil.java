package com.github.black.hole;

/**
 * @author hairen.long
 * @date 2021/1/11
 */
public class SecurityUtil {

    private static final String REPLACE_CHAR = "*";

    public static String around(String str, int left, int right) {
        if (str == null || (str.length() < left + right + 1)) {
            return str;
        }
        String regex = String.format("(?<=\\w{%d})\\w(?=\\w{%d})", left, right);
        return str.replaceAll(regex, REPLACE_CHAR);
    }

    /**
     * 1****************x
     *
     * @param cardNum
     * @return
     */
    public static String idCard(String cardNum) {
        return around(cardNum, 1, 1);
    }

    /**
     * ****2966
     *
     * @param phone
     * @return
     */
    public static String phone(String phone) {
        return around(phone, 3, 4);
    }

    public static void main(String[] args) {
        System.out.println(idCard("15032619840627183x"));
        System.out.println(phone("15087653459"));
    }
}

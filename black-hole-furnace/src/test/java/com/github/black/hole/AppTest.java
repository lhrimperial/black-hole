package com.github.black.hole;

import com.github.black.hole.sboot.util.AesEncryptUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Unit test for simple App. */
public class AppTest {
    public static void main(String[] args) throws Exception {
        String str = "上海普陀区枫桥路快送商圈（(71546)";
        String s = str.substring(0,str.indexOf("("));
        System.out.println(s);
    }

    public static void testSubstring() {
        String str = "汇金路快送商圈(71258)";
        String s = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        System.out.println(s);

        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void testAes() throws Exception {
        String aesKey = "c72XCVbpLUUqB3sRNR2KQwxtqnbqi2bkidGtN9Zi5jP";

        String content = "hello";
        String scr = AesEncryptUtils.encrypt(content, aesKey);
        System.out.println(scr);
        String result = AesEncryptUtils.decrypt(scr, aesKey);
        System.out.println(result);
    }

    private void testBit() {
        int n = 100000000;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n = (n + 1) << 1;
        System.out.println(n);
        BigDecimal a = new BigDecimal(1000000);
        BigDecimal b = new BigDecimal(10);
        System.out.println(
                BigDecimal.valueOf(Math.log(a.doubleValue()))
                        .divide(
                                BigDecimal.valueOf(Math.log(b.doubleValue())),
                                2,
                                RoundingMode.HALF_UP));
    }
}

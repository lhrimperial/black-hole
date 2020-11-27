package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.black.hole.sboot.util.AesEncryptUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Unit test for simple App. */
public class AppTest {
    public static void main(String[] args) throws Exception {
        List<Long> teamIds = Collections.emptyList();
        teamIds.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Test
    public void testSubstring() {
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

package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.sboot.util.AesEncryptUtils;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/** Unit test for simple App. */
public class AppTest {
    public static void main(String[] args) throws Exception {
        Set<Long> set1 = Sets.newHashSet();
        Set<Long> set2 = Sets.newHashSet(1L, 2L, 3L);
        List<Long> list = Sets.difference(set1, set2).stream().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
    }

    private static void validEmoji(String message) {
        for (int i = 0; i < message.length(); i++) {
            if (!isNotEmojiCharacter(message.charAt(i))) {
                throw new IllegalArgumentException("暂不支持Emoji");
            }
        }
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0)
                || (codePoint == 0x9)
                || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
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

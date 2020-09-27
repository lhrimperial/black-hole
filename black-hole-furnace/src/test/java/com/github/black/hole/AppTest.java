package com.github.black.hole;

import com.github.black.hole.sboot.util.AesEncryptUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Unit test for simple App. */
public class AppTest {
    public static void main(String[] args) throws Exception {
        testAes();
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

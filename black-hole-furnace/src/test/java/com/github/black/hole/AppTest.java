package com.github.black.hole;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Unit test for simple App. */
public class AppTest {
    public static void main(String[] args) {
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

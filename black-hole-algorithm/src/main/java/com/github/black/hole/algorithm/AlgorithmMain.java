package com.github.black.hole.algorithm;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author hairen.long
 * @date 2021/1/21
 */
public class AlgorithmMain {

    /**
     * 判断奇偶数：奇数（x & 1 == 1）、偶数（x & 1 == 0）
     *
     * <p>清零最低位的1: y = x & (x - 1)
     *
     * <p>获取最低位的1: y = x & -x
     *
     * <p>清零：0 = x & ~x
     *
     * <p>将x最右边的n位清零：x & (~0 << n)
     *
     * <p>获取x的第n位值（0或者1）：(x >> n) & 1
     *
     * <p>将x的第n位置为1: x | (1 << n)
     *
     * <p>将x的第n位置为0: x & (~(1<<n))
     *
     * <p>将x的最高位至n位（含）清零：x & ((1<<n) - 1)
     *
     * <p>将第n位至0位（含）清零：x & (~((1 << (n + 1)) - 1))
     */
    public static void main(String[] args) {
        int x = 40;
        int digitBit = x & (-x);
        printf(digitBit);
        printf(digitBit - 1);
        int digit = Integer.bitCount(digitBit - 1);
        printf(digit);
        printf(x);
        int y = x ^ (1 << 2);
        printf(y);
        int z = x | (1 << 2);
        printf(z);
    }

    private void test() {
        try (FileInputStream is = new FileInputStream(new File(""))) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    public static void printf(int x) {
        System.out.println(Integer.toBinaryString(x));
    }
}

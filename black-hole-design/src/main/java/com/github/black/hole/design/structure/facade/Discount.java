package com.github.black.hole.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Discount {

    int getDiscount(String discountCode) {
        return Math.abs(discountCode.hashCode()) % 3;
    }
}

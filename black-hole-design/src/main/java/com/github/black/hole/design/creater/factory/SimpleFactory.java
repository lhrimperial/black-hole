package com.github.black.hole.design.creater.factory;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SimpleFactory {

    public static Product getProduct(String productName) {
        if (Strings.isNullOrEmpty(productName)) {
            return null;
        }
        if (Objects.equal(productName, "A")) {
            return new ProductA();
        }
        if (Objects.equal(productName, "B")) {
            return new ProductB();
        }
        return null;
    }
}

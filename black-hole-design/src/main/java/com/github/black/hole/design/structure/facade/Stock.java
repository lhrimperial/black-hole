package com.github.black.hole.design.structure.facade;

import java.util.Random;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Stock {

    boolean hasStock(String product) {
        // 模拟是否还有库存
        return new Random().nextInt(Math.abs(product.hashCode())) > 0;
    }
}

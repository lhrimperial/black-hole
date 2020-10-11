package com.github.black.hole.base.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class ProductPrice {
    int getPrice(String product) {
        // 模拟获取商品价格
        return Math.abs(product.hashCode());
    }
}

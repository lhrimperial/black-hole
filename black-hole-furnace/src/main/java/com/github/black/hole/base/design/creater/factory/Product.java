package com.github.black.hole.base.design.creater.factory;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public interface Product {

    /**
     * 获取价格
     *
     * @return
     */
    String getPrice();

    /**
     * 获取商品名称
     *
     * @return
     */
    String getProductName();
}

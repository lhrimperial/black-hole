package com.github.black.hole.sboot.design.creater.factory;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class FactoryMethodB extends FactoryMethod {

    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
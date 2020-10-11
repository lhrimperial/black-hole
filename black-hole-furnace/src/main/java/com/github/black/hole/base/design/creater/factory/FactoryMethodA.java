package com.github.black.hole.base.design.creater.factory;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class FactoryMethodA extends FactoryMethod {

    @Override
    public Product createProduct() {
        return new ProductA();
    }
}

package com.github.black.hole.design.creater.factory;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class CreateMainTest {

    public static void main(String[] args) {
        simpleFactoryTest();

        factoryMethod();
    }

    public static void factoryMethod(){
        FactoryMethod factoryMethod = new FactoryMethodB();
        Product product = factoryMethod.createProduct();
        printProduct(product);
    }

    public static void simpleFactoryTest() {
        Product product = SimpleFactory.getProduct("A");
        printProduct(product);
    }

    public static void printProduct(Product product) {
        System.out.println(
                "product name: " + product.getProductName() + " ,price: " + product.getPrice());
    }
}

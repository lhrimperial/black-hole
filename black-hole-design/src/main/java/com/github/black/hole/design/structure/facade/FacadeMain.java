package com.github.black.hole.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class FacadeMain {

    public static void main(String[] args) {
        Object info = ProductSalesman.instance.buySomething("银河飞船", "地球", "K1234523");
        System.out.println(info);
    }
}

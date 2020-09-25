package com.github.black.hole.sboot.design.structure.proxy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class ProxyMain {

    public static void main(String[] args) {
        AbstractObject obj = new ProxyObject();
        obj.method1();
        obj.method2();
        obj.method3();
    }
}

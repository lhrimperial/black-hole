package com.github.black.hole.sboot.design.structure.proxy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class TargetObject implements AbstractObject {
    @Override
    public void method1() {
        System.out.println("具体对象的方法1");
    }

    @Override
    public int method2() {
        System.out.println("具体对象的方法2");
        return 0;
    }

    @Override
    public void method3() {
        System.out.println("具体对象的方法3");
    }
}

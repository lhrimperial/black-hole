package com.github.black.hole.sboot.design.structure.plyweight;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Flyweight implements IFlyweight {

    private String value;

    public Flyweight(String value) {
        this.value = value;
    }

    @Override
    public void doSomething() {
        System.out.println(value);
    }
}

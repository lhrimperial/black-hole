package com.github.black.hole.design.structure.plyweight;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SimpleFlyweightMain {

    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        IFlyweight flyweight1, flyweight2, flyweight3, flyweight4;
        flyweight1 = factory.getFlyweight("value1");
        flyweight2 = factory.getFlyweight("value1");
        flyweight3 = factory.getFlyweight("value1");
        flyweight4 = factory.getFlyweight("value2");
        flyweight1.doSomething();
        flyweight2.doSomething();
        flyweight3.doSomething();
        flyweight4.doSomething();
        System.out.println(factory.size());
    }
}

package com.github.black.hole.design.structure.plyweight;

import java.util.HashMap;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class FlyweightFactory {

    HashMap<String, IFlyweight> flyweights = new HashMap<String, IFlyweight>();

    public IFlyweight getFlyweight(String value) {
        IFlyweight flyweight = flyweights.get(value);
        if (flyweight == null) {
            flyweight = new Flyweight(value);
            flyweights.put(value, flyweight);
        }
        return flyweight;
    }

    public int size() {
        return flyweights.size();
    }
}

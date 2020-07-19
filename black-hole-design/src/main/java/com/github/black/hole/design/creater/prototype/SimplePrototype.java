package com.github.black.hole.design.creater.prototype;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SimplePrototype implements Prototype, Cloneable {
    int value;

    @Override
    public Object cloneSelf() {
        SimplePrototype self = new SimplePrototype();
        self.value = value;
        return self;
    }

    public static void main(String[] args) {
        SimplePrototype simplePrototype = new SimplePrototype();
        simplePrototype.value = 500;
        SimplePrototype simplePrototypeClone = (SimplePrototype) simplePrototype.cloneSelf();
        System.out.println(simplePrototypeClone.value);
    }
}

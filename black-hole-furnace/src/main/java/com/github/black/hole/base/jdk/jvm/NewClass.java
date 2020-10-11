package com.github.black.hole.base.jdk.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
public class NewClass {

    private int age;
    private String name;

    public NewClass(int age,String name){
        this.age = age;
        this.name = name;
    }

    public static void main(String[] args) {
        NewClass clazz = new NewClass(12, "andy");
        System.out.println(clazz);
        int cap = tableSizeFor(3);
        Map<String, NewClass> map = new HashMap<>(1);
        map.put("hello", clazz);
    }

    @Override
    public String toString(){
        return "[" + this.name + "," + this
                .age + "]";
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }
}

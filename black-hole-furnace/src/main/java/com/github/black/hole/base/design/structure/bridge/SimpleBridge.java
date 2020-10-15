package com.github.black.hole.base.design.structure.bridge;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SimpleBridge {

    public static void main(String[] args) {
        new LenevoComputer(new Amd()).describe();
        new HaseeComputer(new Intel()).describe();
    }
}
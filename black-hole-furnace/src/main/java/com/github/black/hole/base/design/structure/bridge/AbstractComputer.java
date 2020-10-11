package com.github.black.hole.base.design.structure.bridge;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public abstract class AbstractComputer {

    Cpu cpu;

    public AbstractComputer(Cpu cpu) {
        this.cpu = cpu;
    }

    /** describe */
    public abstract void describe();
}

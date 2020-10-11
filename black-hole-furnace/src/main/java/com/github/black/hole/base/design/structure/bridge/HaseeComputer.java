package com.github.black.hole.base.design.structure.bridge;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class HaseeComputer extends AbstractComputer {

    public HaseeComputer(Cpu cpu) {
        super(cpu);
    }

    @Override
    public void describe() {
        System.out.println("神舟笔记本cpu:" + super.cpu.describe());
    }
}

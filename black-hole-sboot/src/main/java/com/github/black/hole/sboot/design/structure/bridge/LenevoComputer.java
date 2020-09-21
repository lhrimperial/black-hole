package com.github.black.hole.sboot.design.structure.bridge;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class LenevoComputer extends AbstractComputer {

    public LenevoComputer(Cpu cpu) {
        super(cpu);
    }

    @Override
    public void describe() {
        System.out.println("联想笔记本cpu:" + super.cpu.describe());
    }
}

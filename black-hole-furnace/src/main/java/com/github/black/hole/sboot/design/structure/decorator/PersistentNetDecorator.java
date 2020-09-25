package com.github.black.hole.sboot.design.structure.decorator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class PersistentNetDecorator extends PersistentDecorator {

    public PersistentNetDecorator(IPersistentUtil iPersistentUtil) {
        super(iPersistentUtil);
    }

    @Override
    public void persistentMsg(String msg) {
        iPersistentUtil.persistentMsg(msg);
        persistentToNet(msg);
    }

    private void persistentToNet(String msg) {
        System.out.println(msg + " 存入网络的其他地方");
    }
}

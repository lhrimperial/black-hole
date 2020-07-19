package com.github.black.hole.design.structure.decorator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public abstract class PersistentDecorator implements IPersistentUtil {

    IPersistentUtil iPersistentUtil;

    public PersistentDecorator(IPersistentUtil iPersistentUtil) {
        this.iPersistentUtil = iPersistentUtil;
    }

    @Override
    public void persistentMsg(String msg) {
        iPersistentUtil.persistentMsg(msg);
    }
}

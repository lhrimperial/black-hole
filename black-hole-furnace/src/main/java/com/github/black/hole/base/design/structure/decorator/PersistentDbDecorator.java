package com.github.black.hole.base.design.structure.decorator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class PersistentDbDecorator extends PersistentDecorator {

    public PersistentDbDecorator(IPersistentUtil iPersistentUtil) {
        super(iPersistentUtil);
    }

    @Override
    public void persistentMsg(String msg) {
        iPersistentUtil.persistentMsg(msg);
        persistentToDb(msg);
    }

    private void persistentToDb(String msg) {
        System.out.println(msg + " 存入数据库");
    }
}

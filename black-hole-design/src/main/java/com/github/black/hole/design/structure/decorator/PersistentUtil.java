package com.github.black.hole.design.structure.decorator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class PersistentUtil implements IPersistentUtil {

    @Override
    public void persistentMsg(String msg) {
        System.out.println(msg + " 存入文件");
    }
}

package com.github.black.hole.base.design.structure.decorator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public interface IPersistentUtil {
    /**
     * 持久化消息
     *
     * @param msg
     */
    void persistentMsg(String msg);
}

package com.github.black.hole.sboot.design.behavior.strategy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SaveToMysql implements ISaveData {
    @Override
    public void save(Object data) {
        System.out.println("数据：" + data + " 保存到Mysql");
    }
}

package com.github.black.hole.design.behavior.strategy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SaveToFile implements ISaveData {
    @Override
    public void save(Object data) {
        System.out.println("数据：" + data + " 保存到文件");
    }
}

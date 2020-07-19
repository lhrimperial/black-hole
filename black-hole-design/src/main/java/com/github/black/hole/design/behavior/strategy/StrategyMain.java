package com.github.black.hole.design.behavior.strategy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class StrategyMain {

    public static void main(String[] args) {
        Object data = "数据";
        ISaveData saveData = new SaveToRedis();
        SaveClient client = new SaveClient(saveData);
        client.save(data);
        client.setSaveData(new SaveToFile());
        client.save(data);
    }
}

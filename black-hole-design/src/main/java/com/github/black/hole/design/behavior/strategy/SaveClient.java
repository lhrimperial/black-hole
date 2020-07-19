package com.github.black.hole.design.behavior.strategy;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class SaveClient {

    private ISaveData saveData;

    public SaveClient(ISaveData saveData) {
        this.saveData = saveData;
    }

    public void setSaveData(ISaveData saveData) {
        this.saveData = saveData;
    }

    public void save(Object data) {
        saveData.save(data);
    }
}

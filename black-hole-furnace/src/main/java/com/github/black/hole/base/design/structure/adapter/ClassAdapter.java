package com.github.black.hole.base.design.structure.adapter;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class ClassAdapter extends Adaptee implements Target {

    @Override
    public void playFlac(Object src) {
        // 可能需要对src作处理
        playMp3(src);
    }
}

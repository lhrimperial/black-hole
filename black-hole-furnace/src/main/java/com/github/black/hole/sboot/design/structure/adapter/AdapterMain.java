package com.github.black.hole.sboot.design.structure.adapter;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class AdapterMain {

    public static void main(String args[]) {
        Adaptee adaptee = new Adaptee();
        adaptee.playMp3("mp3");
        Target target = new ClassAdapter();
        target.playFlac("flac");
        target = new ObjectAdapter();
        target.playFlac("flac");
    }
}

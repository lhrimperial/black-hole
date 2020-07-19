package com.github.black.hole.design.structure.composite;

import java.util.Iterator;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class ComponentMain {

    public static void main(String[] args) {
        // 根目录
        Component root = new Folder("root");
        Component folder1 = new Folder("java");
        Component folder2 = new Folder("c++");
        Component folder3 = new Folder("c#");
        Component file1 = new File("info.txt");
        // 添加一级目录
        root.addFolder(folder1).addFolder(folder2).addFolder(folder3).addFile(file1);
        folder1.addFile(new File("info.java"));
        Iterator<Component> iterator = root.iterator();
        while (iterator.hasNext()) {
            Component component = iterator.next();
            if (component instanceof Folder) {
                System.out.print("folder：");
            } else {
                System.out.print("file：");
            }
            component.display();
        }
    }
}

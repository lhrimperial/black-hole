package com.github.black.hole.design.structure.composite;

import java.util.Iterator;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public interface Component {
    /**
     * add file
     *
     * @param file
     */
    void addFile(Component file);

    /**
     * add folder
     *
     * @param folder
     * @return
     */
    Component addFolder(Component folder);

    /**
     * remove file
     *
     * @param file
     */
    void removeFile(Component file);

    /**
     * remove folder
     *
     * @param folder
     */
    void removeFolder(Component folder);

    /**
     * get files
     *
     * @return
     */
    List<Component> getFiles();

    /**
     * get folders
     *
     * @return
     */
    List<Component> getFolders();

    /**
     * all
     *
     * @return
     */
    List<Component> getAll();

    /**
     * iterator
     *
     * @return
     */
    Iterator<Component> iterator();

    /** display */
    void display();
}

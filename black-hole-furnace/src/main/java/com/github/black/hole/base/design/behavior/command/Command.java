package com.github.black.hole.base.design.behavior.command;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public interface Command {
    /**
     * execute
     *
     * @param name
     * @throws Exception
     */
    void execute(String name) throws Exception;
}

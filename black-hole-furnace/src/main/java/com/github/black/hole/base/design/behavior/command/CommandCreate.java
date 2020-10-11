package com.github.black.hole.base.design.behavior.command;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class CommandCreate implements Command {
    MakeFile makeFile;

    public CommandCreate(MakeFile makeFile) {
        this.makeFile = makeFile;
    }

    @Override
    public void execute(String name) throws Exception {
        makeFile.createFile(name);
    }
}

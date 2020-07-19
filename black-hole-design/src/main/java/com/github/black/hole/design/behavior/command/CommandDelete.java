package com.github.black.hole.design.behavior.command;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class CommandDelete implements Command {

    MakeFile makeFile;

    public CommandDelete(MakeFile makeFile) {
        this.makeFile = makeFile;
    }

    @Override
    public void execute(String name) throws Exception {
        makeFile.deleteFile(name);
    }
}

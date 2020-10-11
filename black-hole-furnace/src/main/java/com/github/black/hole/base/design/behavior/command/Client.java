package com.github.black.hole.base.design.behavior.command;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Client {

    Command command;

    public Client setCommand(Command command) {
        this.command = command;
        return this;
    }

    public void executeCommand(String name) throws Exception {
        if (command == null) {
            throw new Exception("命令不能为空！");
        }
        command.execute(name);
    }
}

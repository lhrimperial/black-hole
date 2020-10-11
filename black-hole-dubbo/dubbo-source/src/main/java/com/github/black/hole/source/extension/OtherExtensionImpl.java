package com.github.black.hole.source.extension;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
public class OtherExtensionImpl implements MyInterface {

    @Override
    public String sayHello(String name, String type) {
        return this.getClass().getName() + "  " + name + "  " + type;
    }
}

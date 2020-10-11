package com.github.black.hole.source.extension;


import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
@Adaptive
public class MyAdaptiveExtension implements MyInterface {

    @SuppressWarnings("rawtypes")
    @Override
    public String sayHello(String name, String type) {
        ExtensionLoader extensionLoader = ExtensionLoader
                .getExtensionLoader(MyInterface.class);
        MyInterface extension = (MyInterface) extensionLoader
                .getExtension(type);

        return extension.sayHello(name, type);
    }
}

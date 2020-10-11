package com.github.black.hole.source.extension;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
@SPI
public interface MyInterface {

    String sayHello(String name, String type);
}

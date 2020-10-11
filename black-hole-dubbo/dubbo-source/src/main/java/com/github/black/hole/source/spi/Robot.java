package com.github.black.hole.source.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
@SPI
public interface Robot {
    /**
     * say
     */
    void sayHello();
}

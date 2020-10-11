package com.github.black.hole;

import com.github.black.hole.source.spi.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}

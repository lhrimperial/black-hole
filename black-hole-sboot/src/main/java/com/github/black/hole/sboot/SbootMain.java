package com.github.black.hole.sboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hairen.long
 * @date 2020/8/30
 */
@SpringBootApplication
@MapperScan("com.github.black.hole.sboot.server.persistence")
public class SbootMain {

    public static void main(String[] args) {
        SpringApplication.run(SbootMain.class, args);
    }
}

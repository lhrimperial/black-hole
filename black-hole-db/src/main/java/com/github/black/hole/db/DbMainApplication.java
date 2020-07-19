package com.github.black.hole.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hairen.long
 * @date 2020/5/14
 */
@SpringBootApplication
@MapperScan("com.github.**.mapper")
public class DbMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbMainApplication.class, args);
    }
}

package com.github.black.hole.template.orm.database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@Configuration
@MapperScan(basePackages = "com.github.black.hole.template.orm.database")
public class DatabaseConfiguration {}

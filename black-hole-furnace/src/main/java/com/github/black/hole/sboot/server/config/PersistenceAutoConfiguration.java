package com.github.black.hole.sboot.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@MapperScan("com.github.black.hole.sboot.server.persistence")
public class PersistenceAutoConfiguration {
}

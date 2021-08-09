package com.github.black.hole.mybatis.controller;

import com.github.black.hole.mybatis.domain.TestEntity;
import com.github.black.hole.mybatis.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2021/8/8
 */
@RestController
public class TestController {

    @Autowired TestMapper testMapper;

    @RequestMapping("/test/{id}")
    public TestEntity getById(@PathVariable Long id) {
        return testMapper.getById(id);
    }
}

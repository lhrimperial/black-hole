package com.github;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.mybatis.MybatisMain;
import com.github.black.hole.mybatis.domain.TestEntity;
import com.github.black.hole.mybatis.mapper.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** Unit test for simple App. */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisMain.class)
public class AppTest {

    @Autowired TestMapper testMapper;

    @Test
    public void test1() {
        TestEntity entity = testMapper.getById(1L);
        System.out.println(JSON.toJSONString(entity));
    }
}

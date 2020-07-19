package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.db.DbMainApplication;
import com.github.black.hole.db.domain.Departments;
import com.github.black.hole.db.mapper.DepartmentsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/** Unit test for simple App. */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DbMainApplication.class)
public class AppTest {

    @Autowired
    DepartmentsMapper departmentsMapper;

    @Test
    public void shouldAnswerWithTrue() {
        List<Departments> depts = departmentsMapper.findAll();
        System.out.println(JSON.toJSONString(depts));
    }
}

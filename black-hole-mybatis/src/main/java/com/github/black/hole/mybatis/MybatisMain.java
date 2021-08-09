package com.github.black.hole.mybatis;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.mybatis.common.QueryPage;
import com.github.black.hole.mybatis.domain.SubQuery;
import com.github.black.hole.mybatis.mapper.TestMapper;
import com.github.black.hole.mybatis.domain.TestEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2021/8/6
 */
@SpringBootApplication
public class MybatisMain {

    public static void main(String[] args) throws Exception {
                        SpringApplication.run(MybatisMain.class, args);
//        declarative();
    }

    public static void declarative() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
            //            TestEntity entity = testMapper.getById(1L);
            //            System.out.println("sql1:" + entity);

            //                        Map<String, Object> param = new HashMap<>(1);
            //                        param.put("id", 1);
            //                        TestEntity entity =
            //                                sqlSession.selectOne(
            //
            // "com.github.black.hole.mybatis.mapper.TestMapper.getById",
            //             param);
            //                        System.out.println("sql2:" + entity);
            //
//            QueryPage queryPage =
//                    QueryPage.builder()
//                            .pageIndex(1)
//                            .pageSize(10)
//                            .param(TestEntity.builder().id(1).build())
//                            .build();
//            List<TestEntity> list = testMapper.listEntity(queryPage);
//            System.out.println(JSON.toJSONString(list));

            SubQuery query = SubQuery.builder().id(1L).name("andy").build();
            List<TestEntity> list = testMapper.listByQuery(query);
            System.out.println(JSON.toJSONString(list));
        } finally {
            sqlSession.close();
        }
    }
}

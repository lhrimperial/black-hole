package com.github.black.hole.mybatis.mapper;


import com.github.black.hole.mybatis.common.QueryPage;
import com.github.black.hole.mybatis.domain.SubQuery;
import com.github.black.hole.mybatis.domain.TestEntity;
import com.github.black.hole.mybatis.domain.UpperQuery;

import java.util.List;

/**
 *
 */
public interface TestMapper {

    TestEntity getById(Long id);

    List<TestEntity> listEntity(QueryPage queryPage);

    List<TestEntity> listByQuery(UpperQuery query);
}

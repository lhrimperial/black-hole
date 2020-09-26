package com.github.black.hole.sboot.server.repo.impl;

import com.github.black.hole.sboot.common.util.FunctionUtil;
import com.github.black.hole.sboot.server.convert.GradeScoreConvert;
import com.github.black.hole.sboot.server.persistence.entity.GradeScoreDO;
import com.github.black.hole.sboot.server.persistence.mapper.GradeScoreMapper;
import com.github.black.hole.sboot.server.repo.GradeScoreRepo;
import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;
import com.github.black.hole.sboot.server.validateor.ParamCheck;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
@Repository
public class GradeScoreRepoImpl implements GradeScoreRepo {

    @Autowired private GradeScoreMapper gradeScoreMapper;

    @Override
    @ParamCheck
    public void saveOrUpdate(List<GradeScoreBO> records) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        List<GradeScoreDO> insertList = Lists.newArrayList();
        List<GradeScoreDO> updateList = Lists.newArrayList();
        records.forEach(
                record -> {
                    if (Objects.isNull(record.getId())) {
                        insertList.add(GradeScoreConvert.convert2DO(record));
                    } else {
                        updateList.add(GradeScoreConvert.convert2DO(record));
                    }
                });
        if (!CollectionUtils.isEmpty(insertList)) {
            FunctionUtil.batchOperation(gradeScoreMapper::batchInsert, insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            FunctionUtil.batchOperation(gradeScoreMapper::batchUpdate, updateList);
        }
    }

    @Override
    public List<GradeScoreBO> getByCondition(GradeScoreBO condition) {
        if (Objects.isNull(condition) || Objects.isNull(condition.getRuleId())) {
            return Collections.emptyList();
        }
        return GradeScoreConvert.convert2BO(
                gradeScoreMapper.getByCondition(GradeScoreConvert.convert2Condition(condition)));
    }
}

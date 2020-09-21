package com.github.black.hole.sboot.server.persistence.mapper;

import com.github.black.hole.sboot.server.persistence.entity.GradeScoreDO;
import com.github.black.hole.sboot.server.persistence.entity.query.GradeScoreConditionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public interface GradeScoreMapper {

    /**
     * batch insert
     *
     * @param records
     */
    void batchInsert(@Param("records") List<GradeScoreDO> records);

    /**
     * batch update
     *
     * @param records
     */
    void batchUpdate(@Param("records") List<GradeScoreDO> records);

    /**
     * get by condition
     *
     * @param condition
     * @return
     */
    List<GradeScoreDO> getByCondition(GradeScoreConditionDO condition);
}

package com.github.black.hole.sboot.server.repo;

import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public interface GradeScoreRepo {

    /**
     * save or update
     *
     * @param records
     */
    void saveOrUpdate(List<GradeScoreBO> records);

    /**
     * get by condition
     *
     * @param condition
     * @return
     */
    List<GradeScoreBO> getByCondition(GradeScoreBO condition);
}

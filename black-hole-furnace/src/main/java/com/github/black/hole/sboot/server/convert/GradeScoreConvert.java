package com.github.black.hole.sboot.server.convert;

import com.github.black.hole.sboot.common.util.BeanCopyUtil;
import com.github.black.hole.sboot.server.persistence.entity.GradeScoreDO;
import com.github.black.hole.sboot.server.persistence.entity.query.GradeScoreConditionDO;
import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/7/21
 */
public class GradeScoreConvert {

    public static GradeScoreConditionDO convert2Condition(GradeScoreBO scoreBO) {
        return BeanCopyUtil.transform(scoreBO, GradeScoreConditionDO.class);
    }

    public static GradeScoreDO convert2DO(GradeScoreBO scoreBO) {
        return BeanCopyUtil.transform(scoreBO, GradeScoreDO.class);
    }

    public static GradeScoreBO convert2BO(GradeScoreDO scoreDO) {
        return BeanCopyUtil.transform(scoreDO, GradeScoreBO.class);
    }

    public static List<GradeScoreBO> convert2BO(List<GradeScoreDO> scoreList) {
        return BeanCopyUtil.transform(scoreList, GradeScoreBO.class);
    }
}

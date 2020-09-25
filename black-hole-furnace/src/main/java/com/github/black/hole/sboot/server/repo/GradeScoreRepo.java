package com.github.black.hole.sboot.server.repo;

import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
    void saveOrUpdate(@Valid @NotEmpty List<GradeScoreBO> records);

    /**
     * get by condition
     *
     * @param condition
     * @return
     */
    List<GradeScoreBO> getByCondition(GradeScoreBO condition);
}

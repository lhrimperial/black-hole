package com.github.black.hole.sboot.server.service;

import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
public interface GradeScoreService {

    /** save */
    void save();

    /** update */
    void update();

    /**
     * list
     *
     * @return
     */
    List<GradeScoreBO> listScore();
}

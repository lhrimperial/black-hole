package com.github.black.hole.sboot.server.service;

import com.github.black.hole.sboot.server.repo.GradeScoreRepo;
import com.github.black.hole.sboot.server.repo.domain.GradeScoreBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/9/21
 */
@Service
public class GradeScoreServiceImpl implements GradeScoreService {

    @Autowired GradeScoreRepo gradeScoreRepo;

    @Override
    public void save() {
        List<GradeScoreBO> list =
                IntStream.rangeClosed(1, 100)
                        .mapToObj(
                                item ->
                                        GradeScoreBO.builder()
                                                .ruleId(1L)
                                                .rank(item)
                                                .cityId(Long.valueOf(item))
                                                .score(String.valueOf(item))
                                                .dataVersion(1)
                                                .build())
                        .collect(Collectors.toList());

        gradeScoreRepo.saveOrUpdate(list);
    }

    @Override
    public void update() {
        List<GradeScoreBO> list =
                gradeScoreRepo.getByCondition(GradeScoreBO.builder().ruleId(1L).build());
        list.forEach(
                item -> {
                    item.setDataVersion(item.getDataVersion() ^ 0x01);
                });
        gradeScoreRepo.saveOrUpdate(list);
    }

    @Override
    public List<GradeScoreBO> listScore() {
        return gradeScoreRepo.getByCondition(GradeScoreBO.builder().ruleId(1L).build());
    }
}

package com.github.black.hole.db.mapper;

import com.github.black.hole.db.domain.Departments;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/5/14
 */
public interface DepartmentsMapper {

    /**
     *
     * @return
     */
    List<Departments> findAll();
}

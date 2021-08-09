package com.github.black.hole.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hairen.long
 * @date 2021/8/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private long id;
    private String name;
    private Date date;
}

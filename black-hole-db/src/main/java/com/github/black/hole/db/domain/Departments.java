package com.github.black.hole.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/5/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Departments {
    /** code */
    private String deptNo;
    /** name */
    private String deptName;
}

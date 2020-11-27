package com.github.black.hole.template.orm.database.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {
    /** 主键ID */
    private Long id;
    /** 用户名称 */
    private String name;
}

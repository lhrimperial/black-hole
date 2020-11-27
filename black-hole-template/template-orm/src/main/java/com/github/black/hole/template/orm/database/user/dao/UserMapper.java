package com.github.black.hole.template.orm.database.user.dao;

import com.github.black.hole.template.orm.database.user.entity.UserDO;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
public interface UserMapper {

    /**
     * find user by id
     *
     * @param id
     * @return
     */
    UserDO findById(Long id);
}

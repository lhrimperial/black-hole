package com.github.black.hole.template.api.user.service;

import com.github.black.hole.template.api.user.dto.UserDTO;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
public interface UserService {
    /**
     * find user
     *
     * @param userId
     * @return
     */
    UserDTO findUserById(Long userId);
}
